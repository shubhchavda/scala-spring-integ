package com.sabre.apd.loyalty.batch.asr.mapper

import org.springframework.batch.item.file.mapping.FieldSetMapper
import org.springframework.batch.item.file.transform.FieldSet
import com.sabre.apd.loyalty.batch.asr.beans.{RecordType, Itinerary}

/**
 * Created by IntelliJ IDEA.
 * User: SG0209008
 * Date: 4/6/11
 * Time: 10:34 PM
 * To change this template use File | Settings | File Templates.
 */

class ItineraryFieldSetMapper extends FieldSetMapper[Itinerary]{
  def mapFieldSet(fs : FieldSet) : Itinerary = {

    val recordType = new RecordType(fs.readString("id"), fs.readString("qualifier"))

    return new Itinerary(recordType, fs.readString("issueDate"),
                          fs.readString("ticketDocNumber"),
                          fs.readString("ticketDocNumberCheckDigit"),
                          fs.readString("segmentIdentifier"),
                          fs.readString("stopOverCode"),
                          fs.readString("origin"),
                          fs.readString("destination"),
                          fs.readString("carrier"),
                          fs.readString("flightNumber"),
                          fs.readString("classOfService"),
                          fs.readString("flightDate"),
                          fs.readString("fareBasis"))
  }

}

object ItineraryFields extends Enumeration{

}