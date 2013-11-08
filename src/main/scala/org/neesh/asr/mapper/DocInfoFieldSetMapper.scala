package com.sabre.apd.loyalty.batch.asr.mapper

import org.springframework.batch.item.file.mapping.FieldSetMapper
import org.springframework.batch.item.file.transform.FieldSet
import com.sabre.apd.loyalty.batch.asr.beans.{RecordType, TKTDocIdentification}

/**
 * Created by IntelliJ IDEA.
 * User: SG0209008
 * Date: 4/6/11
 * Time: 10:33 PM
 * To change this template use File | Settings | File Templates.
 */

class DocInfoFieldSetMapper extends FieldSetMapper[TKTDocIdentification]{
  def mapFieldSet(fs : FieldSet) : TKTDocIdentification = {
    val recordType = new RecordType(fs.readString("id"), fs.readString("qualifier"))

    return new TKTDocIdentification(recordType, fs.readString("docNumber"),
                       fs.readString("couponIndicator"),
                        fs.readString("conjTKTIndicator"),
                        fs.readString("tourCode"),
                        fs.readString("txnCode"),
                        fs.readString("pnrNumber"))

  }
}

object DocInfoFields extends Enumeration{

}