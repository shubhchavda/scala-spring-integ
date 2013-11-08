package com.sabre.apd.loyalty.batch.asr.mapper

import org.springframework.batch.item.file.mapping.FieldSetMapper
import org.springframework.batch.item.file.transform.FieldSet
import com.sabre.apd.loyalty.batch.asr.beans.{DocAmounts, RecordType, StandardDocAmounts}

/**
 * Created by IntelliJ IDEA.
 * User: SG0209008
 * Date: 4/6/11
 * Time: 10:33 PM
 * To change this template use File | Settings | File Templates.
 */

class DocAmountsFieldSetMapper extends FieldSetMapper[DocAmounts]{
  def mapFieldSet(fs : FieldSet) : DocAmounts = {
    val recordType = new RecordType(fs.readString("id"), fs.readString("qualifier"))

    return new DocAmounts(recordType, fs.readString("fare"),
                          fs.readString("equivalentFare"),
                          fs.readString("fareCurrency"),
                          fs.readString("equivalentFareCurrency"))
  }
}

object DocAmountsFields extends Enumeration{

}

