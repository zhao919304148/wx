package com.sys.redis;

import java.io.IOException;
import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

/**
 * 基于redis的Dao操作支持类，底层支持类
 * @author 许宝众
 *
 */
public class RedisDaoSupport {
	private ThreadLocal<Jedis> jedisThreadLocal=new ThreadLocal<Jedis>();
	private ThreadLocal<Transaction> txThreadLocal=new ThreadLocal<Transaction>();
	private JedisPool jedisPool;
	public RedisDaoSupport(JedisPool jedisPool) {
		this.jedisPool=jedisPool;
	}
	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}
	/**
	 * 取当前线程的jedis
	 * @return 
	 */
	public Jedis getCurrentJedis(){
		Jedis jedis = jedisThreadLocal.get();
		if(jedis!=null){
			return jedis;
		}else{
			jedis = jedisPool.getResource();
			jedisThreadLocal.set(jedis);
			return jedis;
		}
	}
	public void closeCurrentJedis(){
		Jedis jedis = jedisThreadLocal.get();
		if(jedis==null){
			throw new RuntimeException("current jedis is null");
		}
		jedis.close();
		jedisThreadLocal.set(null);
	}
	/**
	 * 监视指定的keys并开启事务
	 * @param keys
	 * @return
	 */
	protected Transaction watchKeysAndBeginTx(String... keys){
		Jedis jedis = getCurrentJedis();
		if(keys!=null&&keys.length>0){
			jedis.watch(keys);
		}
		Transaction tx = jedis.multi();
		txThreadLocal.set(tx);
		return tx;
	}
	/**
	 * 提交事务
	 * @return
	 */
	protected List<Object> commit() {
		Transaction tx = txThreadLocal.get();
		List<Object> res = tx.exec();
		return res;
	}
	/**
	 * 放弃事务取消监视
	 * @return
	 */
	protected void discardTxAndUnwatch() {
		Transaction tx = txThreadLocal.get();
		tx.discard();
	}
	/**
	 * redis事务模板访法,自动提交事务，实现类中不可使用tx.exec()
	 * @param command
	 * 			实现command中的doCommand()方法
	 * @return
	 */
	public Object executeCommand(RedisCommand command){
		return this.executeCommand(command, true);
	}
	/**
	 * redis事务模板访法
	 * @param command
	 * 			实现command中的doCommand()方法
	 * @param autoCommit
	 * 			是否使用模板方式自动提交，true时在command中不能使用tx.exec()
	 * @return
	 */
	public Object executeCommand(RedisCommand command,boolean autoCommit){
		String[] keys = command.getWatchKeys();
		Transaction tx = this.watchKeysAndBeginTx(keys);
		try{
			Object result = command.doCommands(tx);
			if(autoCommit){
				this.commit();
			}
			getCurrentJedis().unwatch();
			return result;
		}catch(Exception ex){
			this.discardTxAndUnwatch();
			throw new RuntimeException(ex);
		}finally{
			try {
				tx.close();
				this.closeCurrentJedis();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}
	
}
