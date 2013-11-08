package org.neesh.asr

import beans._
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.file.transform.FieldSet
import reflect.BeanProperty
import org.springframework.batch.item.file.mapping.FieldSetMapper
import collection.mutable.ListBuffer

/**
 * User: Shubh Chavda
 * Date: 3/27/11
 * Time: 3:03 PM
 * To change this template use File | Settings | File Templates.
 */

class StationDocReader extends ItemReader[Map[String, List[TX]]] {

  @BeanProperty var fieldSetReader: ItemReader[FieldSet] = _

  @BeanProperty var stationHeaderFieldMapper: FieldSetMapper[StationHeader] = _
  @BeanProperty var txHeaderFieldMapper: FieldSetMapper[TXHeader] = _
  @BeanProperty var docInfoFieldMapper: FieldSetMapper[TKTDocIdentification] = _
  @BeanProperty var stdDocAmountsFieldMapper: FieldSetMapper[StandardDocAmounts] = _
  @BeanProperty var docAmountsFieldMapper: FieldSetMapper[DocAmounts] = _
  @BeanProperty var commissionFieldMapper: FieldSetMapper[Commission] = _
  @BeanProperty var itineraryFieldMapper: FieldSetMapper[Itinerary] = _
  @BeanProperty var paxFieldMapper: FieldSetMapper[PAX] = _
  @BeanProperty var fareCalculationFieldMapper: FieldSetMapper[FareCalculation] = _
  @BeanProperty var miscFieldMapper: FieldSetMapper[Misc] = _
  @BeanProperty var fopFieldMapper: FieldSetMapper[FOP] = _

  @throws(classOf[Exception])
  override def read(): Map[String, List[TX]] = {
    val result: ResultHolder = new ResultHolder(new ListBuffer[TX], new ListBuffer[TX], new ListBuffer[TX], new ListBuffer[TX])
    var notDone = true
    while (notDone) {
      notDone = process(fieldSetReader, result)
    }
    if (result.finished) {
      return null
    }
    Map("SALE" -> result.purchases.toList, "VOID" -> result.voids.toList, "REFUND" -> result.refunds.toList)
  }

  private def process(reader: ItemReader[FieldSet], result: ResultHolder): Boolean = {

    val fs = reader.read
    if (fs == null) {
      result.finished = true
      return false
    }
    var status = true
    val recordType = getRecordType(fs)

    recordType match {
      case Some(RecordType("BOH", "03")) => processStationTX(fs, reader, result)
      case Some(RecordType("BFT", "99")) => {status = false;println("End of File Reached..")}
      case None => println("Other record")
      case _ => println("wild card match")
    }
    status
  }

  private def getRecordType(fs: FieldSet): Option[RecordType] = {
    if (!fs.hasNames) {
      return None
    }
    Some(new RecordType(fs.readString("id"), fs.readString("qualifier")))
  }

  private def processStationTX(stationRecord: FieldSet, reader: ItemReader[FieldSet], result: ResultHolder) = {
    var txHeader: TXHeader = null
    var docInfo: ListBuffer[TKTDocIdentification] = null
    var stdDocAmounts: ListBuffer[StandardDocAmounts] = null
    var docAmounts: DocAmounts = null
    var commissions: ListBuffer[Commission] = null
    var itinerary: ListBuffer[Itinerary] = null
    var misc: ListBuffer[Misc] = null
    var fareCalculation: ListBuffer[FareCalculation] = null
    var fop: ListBuffer[FOP] = null
    var pax: PAX = null

    def initTX = {
      docInfo = new ListBuffer[TKTDocIdentification]
      stdDocAmounts = new ListBuffer[StandardDocAmounts]
      commissions = new ListBuffer[Commission]
      itinerary = new ListBuffer[Itinerary]
      misc = new ListBuffer[Misc]
      fareCalculation = new ListBuffer[FareCalculation]
      fop = new ListBuffer[FOP]

    }

    var stationFinished = false

    val stationHeader = stationHeaderFieldMapper.mapFieldSet(stationRecord)
    // now process all TX
    def storeTX: Unit = {
      if (docInfo != null) {
        val tx = new TX(stationHeader, txHeader, docInfo.toList, stdDocAmounts.toList, docAmounts,
          commissions.toList, itinerary.toList, misc.toList, pax, fareCalculation.toList, fop.toList)

//        println(tx.toXML)

        getTXType(docInfo.toList) match {
          case "V" => result.voids += tx
          case "RF" => result.refunds += tx
          case "RP" => result.refunds += tx
          case "SALE" => result.purchases += tx
          case _ => println("Invalid transaction type")
        }
      }

      initTX
    }
    while (!stationFinished) {
      val record = reader.read()
      val recordType = getRecordType(record)

      recordType match {
        case Some(RecordType("BKT", "06")) => {
          println("Found new TX")
          // add prev tx to result
          storeTX
          txHeader = txHeaderFieldMapper.mapFieldSet(record)
        }
        case Some(RecordType("BKS", "24")) => docInfo += docInfoFieldMapper.mapFieldSet(record)
        case Some(RecordType("BKS", "30")) => stdDocAmounts += stdDocAmountsFieldMapper.mapFieldSet(record)
        case Some(RecordType("BKS", "39")) => commissions += commissionFieldMapper.mapFieldSet(record)
        case Some(RecordType("BKI", "63")) => itinerary += itineraryFieldMapper.mapFieldSet(record)
        case Some(RecordType("BAR", "64")) => docAmounts = docAmountsFieldMapper.mapFieldSet(record)
        case Some(RecordType("BAR", "65")) => pax = paxFieldMapper.mapFieldSet(record)
        case Some(RecordType("BMP", "70")) => misc += miscFieldMapper.mapFieldSet(record)
        case Some(RecordType("BKF", "81")) => fareCalculation += fareCalculationFieldMapper.mapFieldSet(record)
        case Some(RecordType("BKP", "84")) => fop += fopFieldMapper.mapFieldSet(record)
        case Some(RecordType("BOT", "93")) => {
          stationFinished = true;
          storeTX
        }
        case Some(RecordType("BOT", "94")) => {
          stationFinished = true; storeTX
        }
        case _ => println("Unknown type " + record)
      }


    }

    println("Finished station..")
  }

  private def getTXType(docInfo: List[TKTDocIdentification]): String = {
    val foundVal: Option[TKTDocIdentification] = docInfo.find((docInfo => docInfo.txnCode != null))
    foundVal match {
      case Some(docInfo: TKTDocIdentification) => docInfo.txnCode
      case None => ""
    }
  }

  class ResultHolder(val purchases: ListBuffer[TX], val voids: ListBuffer[TX],
                     val refunds: ListBuffer[TX], val exchanges: ListBuffer[TX]) {
    var finished = false
  }

}

