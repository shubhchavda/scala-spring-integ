import com.sabre.apd.loyalty.batch.asr.beans.TX
import javax.jms.{Message, Session}
import org.springframework.batch.core.launch.support.SimpleJobLauncher
import org.springframework.batch.core.{JobParametersBuilder, JobParameters, Job}
import org.springframework.context.support.ClassPathXmlApplicationContext
import org.springframework.jms.core.{MessageCreator, JmsTemplate}

/**
 * Created by IntelliJ IDEA.
 * User: SG0209008
 * Date: 3/24/11
 * Time: 2:14 PM
 * To change this template use File | Settings | File Templates.
 */

object Main {

    def main(args:Array[String]){

        val ctx = new ClassPathXmlApplicationContext("asrBatchJobs.xml")
        println("Context created")

//        val jmsTemplate : JmsTemplate = ctx.getBean("jmsTemplate").asInstanceOf[JmsTemplate]
//        jmsTemplate.send("myTestQueue", new MessageCreator{
//          def createMessage(session: Session) : Message = {
//            return session.createTextMessage("My Simple Test")
//          }
//        })
//
//      println("Message Sent!!!")

        val jobLauncher : SimpleJobLauncher = ctx.getBean("jobLauncher").asInstanceOf[SimpleJobLauncher]

        val job : Job = ctx.getBean("asrJob").asInstanceOf[Job]

        val jobParam : JobParameters = new JobParametersBuilder().toJobParameters

        jobLauncher.run(job, jobParam)
    }

}