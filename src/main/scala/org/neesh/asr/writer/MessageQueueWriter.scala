package com.sabre.apd.loyalty.batch.asr.writer

import org.springframework.batch.item.ItemWriter
import com.sabre.apd.loyalty.batch.asr.beans.TX
import actors.{Future, Futures}
import org.springframework.jms.core.{MessageCreator, JmsTemplate}
import javax.jms.{Message, Session, JMSException}
//import collection.JavaConversions._
//import collection.mutable.Buffer

/**
 * User: SG0209008
 * Date: 4/30/11
 * Time: 4:44 PM
 * To change this template use File | Settings | File Templates.
 */

class MessageQueueWriter(val jmsTemplate: JmsTemplate) extends ItemWriter[Map[String, List[TX]]] {

  @throws(classOf[Exception])
  override def write(items: java.util.List[_ <: Map[String, List[TX]]]): Unit = {

    processTKT(items.get(0).getOrElse("SALE", null))

  }

  private def processTKT(lst: List[TX]): Unit = {
    val tasks: List[Future[Unit]] = lst.map(item => Futures.future {
      sendMessage(item)
    })

    tasks.toList.map(future => future.apply())
    //    Futures.awaitAll(60000, tasks)
  }

  private def sendMessage(payload: TX) {
    //    jmsTemplate.send("asr.queue", new TKTMessageCreator(payload))
    println("Handling TKT " + payload.toXML)
  }

  private class TKTMessageCreator(val tkt: TX) extends MessageCreator {

    @throws(classOf[JMSException])
    override def createMessage(session: Session): Message = {
      val msg: Message = session.createTextMessage(tkt.toXML.text)
      msg
    }

  }

}