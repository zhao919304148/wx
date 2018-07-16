package com.sys.redis;

import redis.clients.jedis.Transaction;

/**
 * redis事务命令接口
 * @author 许宝众
 *
 */
public abstract class RedisCommand {
	/**
	 * 事务启动前，需要监视的keys
	 */
	private String[] watchKeys;
	/**
	 * 通过tx执行期望执行的命令，无需关心事务管理
	 * @param tx
	 * @return
	 */
	public abstract Object doCommands(Transaction tx);
	
	public RedisCommand(String[] watchKeys) {
		this.watchKeys=watchKeys;
	}

	public String[] getWatchKeys() {
		return watchKeys;
	}

	public void setWatchKeys(String[] watchKeys) {
		this.watchKeys = watchKeys;
	}
	
}
