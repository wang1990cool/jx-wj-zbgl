package io.jianxun.common.utils;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.collect.Maps;

import io.jianxun.common.service.exception.ServiceException;

public abstract class AbstractSerialNumber {

	private static Map<SerialNumberKey, SerialNumberEntity> cache = Maps
			.newHashMap();

	public class SerialNumberKey {
		private String prefix;

		private int range = 1;

		public SerialNumberKey(String prefix, int range) {
			super();
			this.prefix = prefix;
			this.range = range;
		}

		public String getPrefix() {
			return prefix;
		}

		public void setPrefix(String prefix) {
			this.prefix = prefix;
		}

		public int getRange() {
			return range;
		}

		public void setRange(int range) {
			this.range = range;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == this)
				return true;
			if (obj instanceof SerialNumberKey) {
				SerialNumberKey other = (SerialNumberKey) obj;
				return this.prefix.equals(other.getPrefix());
			}
			return false;
		}

		@Override
		public int hashCode() {
			return this.prefix.hashCode();
		}

		@Override
		public String toString() {
			return this.prefix + "[" + range + "]";
		}

	}

	public class SerialNumberEntity {

		private AtomicInteger num = new AtomicInteger(0);

		private SerialNumberEntity(int num) {
			super();
			this.num = new AtomicInteger(num);
		}

		public AtomicInteger getNum() {
			return num;
		}

	}

	public abstract int getMaxSerialNumber(String prefix) throws ServiceException;

	public void initCache() {
		cache.clear();
	}

	public void initSerialNumber(SerialNumberKey key) throws ServiceException {
		cache.remove(key);
		cache.put(key,
				new SerialNumberEntity(getMaxSerialNumber(key.getPrefix())));
	}

	public String getSerialNumberEntityNumber(String keyStr) throws ServiceException {
		SerialNumberKey key = getKey(keyStr);
		SerialNumberEntity entity = getSerialNumberEntityFromCache(key);
		return format(key.getPrefix(), nextNum(entity));
	}

	protected String format(String prefix, int nextNum) {
		return prefix + format(getNumWidth(), getChar()).format(nextNum);
	}

	protected int getNumWidth() {
		return 4;
	}

	protected char getChar() {
		return '0';
	}

	protected int getRange() {
		return 1;
	}
	
	protected DecimalFormat format(int width,char c){
		if(width<1)
			width = 1;
		char[] chs = new char[width];
		for (int i = 0; i < width; i++) {
			chs[i] = c;
		}
		return new DecimalFormat(new String(chs));
		
	}

	private SerialNumberKey getKey(String keyStr) {
		SerialNumberKey key = new SerialNumberKey(keyStr, getRange());
		return key;
	}

	private SerialNumberEntity getSerialNumberEntityFromCache(
			SerialNumberKey key) throws ServiceException {
		SerialNumberEntity entity = cache.get(key);
		if (entity == null) {
			initSerialNumber(key);
			entity = cache.get(key);
		}
		return entity;
	}

	private int nextNum(SerialNumberEntity serialNumberEntity) {
		return serialNumberEntity.getNum().addAndGet(getRange());
	}

}
