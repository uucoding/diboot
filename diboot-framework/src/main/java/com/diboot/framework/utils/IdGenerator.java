package com.diboot.framework.utils;

import com.diboot.framework.config.BaseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数字类型的ID生成器控件
 * @author Mazc@dibo.ltd
 * @version 20161107
 * Copyright @ www.dibo.ltd
 */
public class IdGenerator {
	private static final Logger logger = LoggerFactory.getLogger(IdGenerator.class);

	private long workerId = 1; // 当前程序id标识
	private long dataCenterId = 1; // 数据中心id标识
	private long sequence = 0L; // 序列号

	private long workerIdBits = 5L;
	private long datacenterIdBits = 5L;
	private long maxWorkerId = -1L ^ (-1L << workerIdBits);
	private long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
	private long sequenceBits = 12L;

	private long workerIdShift = sequenceBits;
	private long datacenterIdShift = sequenceBits + workerIdBits;
	private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
	private long sequenceMask = -1L ^ (-1L << sequenceBits);

	private long lastTimestamp = -1L;
	/**
	 * 时间戳差值
	 */
	private static long twepoch = 1288834974657L;

	public IdGenerator() {
		if(V.notEmpty(BaseConfig.getProperty("id.generator.workerid"))){
			workerId = Long.parseLong(BaseConfig.getProperty("id.generator.workerid"));
		}
		if(V.notEmpty(BaseConfig.getProperty("id.generator.datacenterid"))){
			dataCenterId = Long.parseLong(BaseConfig.getProperty("id.generator.datacenterid"));
		}
		// sanity check for workerId
		if (workerId > maxWorkerId || workerId < 0) {
			logger.warn(String.format("Worker Id 不能大于 %d 或小于 0", maxWorkerId));
			workerId = 1;
		}
		if (dataCenterId > maxDatacenterId || dataCenterId < 0) {
			logger.warn(String.format("DataCenter Id 不能大于 %d 或小于 0", maxDatacenterId));
			dataCenterId = 1;
		}
		logger.info(String.format("ID生成器初始化成功. DataCenterId = %d, Worker Id = %d", dataCenterId, workerId));
	}

	/***
	 * 生成下一个id
	 * @return
	 */
	public synchronized long nextId() {
		long timestamp = timeGen();
		if (timestamp < lastTimestamp) {
			logger.error("服务器时钟错误！");
			throw new RuntimeException("服务器时钟错误，无法生成ID！");
		}
		if (lastTimestamp == timestamp) {
			sequence = (sequence + 1) & sequenceMask;
			if (sequence == 0) {
				timestamp = tilNextMillis(lastTimestamp);
			}
		} else {
			sequence = 0L;
		}

		lastTimestamp = timestamp;

		return (timestamp << timestampLeftShift) | (dataCenterId << datacenterIdShift) | (workerId << workerIdShift) | sequence;
	}

	/***
	 * 日期时间相关处理
	 * @return
	 */
	private static long timeGen() {
		return System.currentTimeMillis() - twepoch;
	}
	private static long tilNextMillis(long lastTimestamp) {
		long timestamp = timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = timeGen();
		}
		return timestamp;
	}

	/**
	 * 获得随机UUID
	 * @return
	 */
	public static String newUuid() {
		return S.newUuid();
	}

}