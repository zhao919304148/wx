package com.wap.dzsw.rabbitmq;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
public class LimitTimeRobRabbitmqCallBack implements RabbitTemplate.ConfirmCallback{
	private static final String UPDATE_SQL="update limit_time_rob_get_his set isqueue = ? where id = ? ;";
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Override
	public void confirm(final CorrelationData correlationData, boolean ack) {
		if(correlationData!=null&&StringUtils.isNotBlank(correlationData.getId())){
			String isQueue = ack?"1":"0";
			jdbcTemplate.update(UPDATE_SQL, isQueue,correlationData.getId());
		}
	}
}
