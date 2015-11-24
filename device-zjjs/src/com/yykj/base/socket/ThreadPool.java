package com.yykj.base.socket;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.yykj.base.util.PropertiesUtil;

/**
 * 线程池
 * @author QinShuJin
 * 2015年7月16日 下午12:07:30
 */
public class ThreadPool {
	private ThreadPool (){}
	private static ExecutorService pool = null;
	//CPU数量
	private static int CPU_CONT = Integer.parseInt(PropertiesUtil.getInstance().get("CPU_COUNT","2"));
	//单颗CPU线程数量 
	private static int ONE_CPU_THRED_COUNT = Integer.parseInt(PropertiesUtil.getInstance().get("ONE_CPU_THRED_COUNT","50"));
	//队列大小
	private static int QUEUE_SIZE = Integer.parseInt(PropertiesUtil.getInstance().get("QUEUE_SIZE","500"));
	
	private static ThreadPool instance;  
	
	public static ThreadPool getInstance() {
		if (instance == null) {
			instance = new ThreadPool();
		}
		return instance;
	}


	public ExecutorService getPool() {
		//工作线程大小
		//CPU密集型应用线程计算公式   N+1
		//I/O密集型应用线程计算公式  2N+1
		int maxThredSize = 2*(CPU_CONT * ONE_CPU_THRED_COUNT)+1;
		int initThredSize = maxThredSize/2;
		if (pool == null) {
			System.out.println("初始化线程个数："+initThredSize +" 最大线程个数："+maxThredSize  +" 队列数："+QUEUE_SIZE);
			pool = new ThreadPoolExecutor(
					2, 	//初始线程
					3, 	//最大线程数
					30, //30秒不执行任务就销毁
					TimeUnit.SECONDS, //秒
					new ArrayBlockingQueue<Runnable>(QUEUE_SIZE), 		//有界对列
					Executors.defaultThreadFactory(),			//执行程序创建新线程时使用的工厂
					new ThreadPoolExecutor.CallerRunsPolicy());	//由于超出线程范围和队列容量而使执行被阻塞时所使用的处理程序(当前是调用自身执行)
		}
		return pool;
	}
}
