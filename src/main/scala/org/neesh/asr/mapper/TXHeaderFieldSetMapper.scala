package com.sabre.apd.loyalty.batch.asr.mapper

import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.file.transform.FieldSet
import org.springframework.batch.item.file.mapping.FieldSetMapper
import com.sabre.apd.loyalty.batch.asr.beans.{RecordType, TXHeader, TX}

/**
 * Created by IntelliJ IDEA.
 * User: SG0209008
 * Date: 4/6/11
 * Time: 10:30 PM
 * To change this template use File | Settings | File Templates.
 */

class TXHeaderFieldSetMapper extends FieldSetMapper[TXHeader] {
  def mapFieldSet(fs : FieldSet) : TXHeader = {
    val recordType = new RecordType(fs.readString("id"), fs.readString("qualifier"))

    return new TXHeader(recordType, fs.readString("docType"), fs.readString("eTKT"), fs.readString("tranRefNumber"))
  }

}