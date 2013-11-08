package org.neesh.asr.mapper

import org.springframework.batch.item.file.mapping.FieldSetMapper
import org.springframework.batch.item.file.transform.FieldSet
import org.neesh.asr.beans.{RecordType, FareCalculation}

/**
 * Created by IntelliJ IDEA.
 * User: Shubh Chavda
 * Date: 4/6/11
 * Time: 10:35 PM
 * To change this template use File | Settings | File Templates.
 */

class FareCalculationFieldSetMapper extends FieldSetMapper[FareCalculation]{

  def mapFieldSet(fs : FieldSet) : FareCalculation = {
    val recordType = new RecordType(fs.readString("id"), fs.readString("qualifier"))

    return new FareCalculation(recordType, fs.readString("text"))
  }

}
