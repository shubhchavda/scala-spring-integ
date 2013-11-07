package com.sabre.apd.loyalty.batch.asr.mapper

import org.springframework.batch.item.file.mapping.FieldSetMapper
import org.springframework.batch.item.file.transform.FieldSet
import com.sabre.apd.loyalty.batch.asr.beans.{RecordType, Commission}

/**
 * Created by IntelliJ IDEA.
 * User: SG0209008
 * Date: 4/6/11
 * Time: 10:33 PM
 * To change this template use File | Settings | File Templates.
 */

class CommissionFieldSetMapper extends FieldSetMapper[Commission] with FieldSanitizer{
  def mapFieldSet(fs : FieldSet) : Commission = {
    val recordType = new RecordType(fs.readString("id"), fs.readString("qualifier"))

    return new Commission(recordType,
                          fs.readString("docNumber"),
                          sanitize(fs.readString("rate")),
                          sanitize(fs.readString("amount")),
                          fs.readString("suppType"),
                          sanitize(fs.readString("suppRate")),
                          sanitize(fs.readString("suppAmount")),
                          sanitize(fs.readString("effectiveAmount")))
  }
}

object CommissionFields extends Enumeration{

}