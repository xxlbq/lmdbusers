/**
 * $Id: StatusThread.java,v 1.1 2006/10/28 03:29:55 lijc Exp $
 * 查询分析器相关
 */
package com.livedoor.dbm.components.queryanalyzer;

/**
 * <p>Title: 显示状态线程</p> 
 * <p>Description: 显示脚本执行状态</p> 
 * <p>Copyright: Copyright (c) 2006</p> 
 * <p>Company: 英極軟件開發（大連）有限公司</p>
 * 
 * @author <a href="mailto:lijc@livedoor.cn">LiJicheng</a>
 * @version 1.0
 */
public class StatusThread extends Thread {

    private QueryThread queryThread;
    private long startTime;

    public StatusThread(QueryThread queryThread, long startTime) {
        this.queryThread = queryThread;
        this.startTime = startTime;
    }

    @Override
    public void run() {

        long l = 0L;
        long l1 = 0L;
        long l2 = 0L;
        long l3 = 0L;
        for (boolean flag = true; flag;) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException interruptedexception) {
                flag = false;
            }
            
            long l5 = System.currentTimeMillis() - startTime;
            long l6 = l5;
            if (l5 > 0L) {
                long l4 = l5 % 1000L;
                l5 -= l4;
                if (l5 > 0L) {
                    l5 /= 1000L;
                    l2 = l5 % 60L;
                    l5 -= l2;
                    if (l5 > 0L) {
                        l5 /= 60L;
                        l1 = l5 % 60L;
                        l5 -= l1;
                        if (l5 > 0L) {
                            l5 /= 60L;
                            l = l5 % 60L;
                            l5 -= l;
                        }
                    }
                }
            }
            boolean flag1 = false;
            String s = "";
            if (l > 0L) {
                s = s + l + "h ";
                flag1 = true;
            }
            if (l1 > 0L || flag1) {
                s = s + l1 + "m ";
                flag1 = true;
            }
            if (l2 > 0L || flag1) {
                s = s + l2 + "s ";
                boolean flag2 = true;
            }
            if (l6 < 1000L)
                s = s + l6 + "ms ";

            queryThread.setStatusText("[Time: " + s + "]", true);
        }
    }
}
