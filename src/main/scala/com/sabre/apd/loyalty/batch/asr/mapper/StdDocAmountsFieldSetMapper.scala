package com.sabre.apd.loyalty.batch.asr.mapper

import org.springframework.batch.item.file.mapping.FieldSetMapper
import org.springframework.batch.item.file.transform.FieldSet
import com.sabre.apd.loyalty.batch.asr.beans.{RecordType, StandardDocAmounts}

/**
 * Created by IntelliJ IDEA.
 * User: SG0209008
 * Date: 4/6/11
 * Time: 10:33 PM
 * To change this template use File | Settings | File Templates.
 */

class StdDocAmountsFieldSetMapper extends FieldSetMapper[StandardDocAmounts] with FieldSanitizer{
  def mapFieldSet(fs : FieldSet) : StandardDocAmounts = {
    val recordType = new RecordType(fs.readString("id"), fs.readString("qualifier"))

    return new StandardDocAmounts(recordType, sanitize(fs.readString("baseFare")),
                          sanitize(fs.readString("netFare")),
                          sanitize(fs.readString("taxCode1")),
                          sanitize(fs.readString("taxAmount1")),
                          sanitize(fs.readString("taxCode2")),
                          sanitize(fs.readString("taxAmount2")),
                          sanitize(fs.readString("currencyType")))
  }

}

object StdDocAmountsFields extends Enumeration{

}

