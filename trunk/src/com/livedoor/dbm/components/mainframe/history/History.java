package com.livedoor.dbm.components.mainframe.history;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.TreeSet;
import com.livedoor.dbm.components.mainframe.DBMFrame;
import com.livedoor.dbm.util.DBMPropertiesUtil;
/**
 * <p>
 * Description: History
 * </p>
 * Copyright: Copyright (c) 2006 Company: 英極軟件開發（大連）有限公司
 * 
 * @author chepeng
 * @version 1.0
 */
public class History {
	
	public static class LongComparator implements Comparator {
		/**
		 * [機 能] 构造用 Comparator 来控制数据结构 TreeSet的顺序
		 * <p>
		 * [解 説] 构造用 Comparator 来控制数据结构 TreeSet的顺序 。
		 * <p>
		 * 
		 * [備 考] なし
		 */	
		public final int compare(Object obj, Object obj1) {
			if (!(obj instanceof Long) && !(obj1 instanceof Long))
				return 0;
			if (!(obj instanceof Long) && (obj1 instanceof Long))
				return -1;
			if ((obj instanceof Long) && !(obj1 instanceof Long))
				return 1;
			long l = ((Long) obj).longValue() - ((Long) obj1).longValue();
			if (l == 0L)
				return 0;
			return l >= 0L ? 1 : -1;
		}

		public LongComparator() {
		}
	}

	@SuppressWarnings("unused")
	private static final String CONNECTION_TYPE = "CONNECTION_TYPE";

	@SuppressWarnings("unused")
	private static final String STMT_TIME = "STMT_TIME";

	private static ArrayListTableModel _model = null;

	private static DBMHistoryDialog _history = null;

	public History() {
	}
	/**
	 * [機 能] 初始化History
	 * <p>
	 * [解 説] 初始化History 。
	 * <p>
	 * 
	 * [備 考] なし
	 */
	static {
		String as[] = new String[3];
		as[0] = "Date";
		as[1] = "Server Type";

		as[2] = "SQL Statement";
		_model = new ArrayListTableModel(as, new ArrayList());
		loadHistoryFromDisk();
	}
	/**
	 * [機 能] show History Dialog
	 * <p>
	 * [解 説] show History Dialog 。
	 * <p>
	 * 
	 * [備 考] なし
	 * 
	 * @param dbmframe
	 */
	@SuppressWarnings("deprecation")
	public static synchronized void showHistory(DBMFrame dbmframe) {
		if (_history == null) {
			_history = new DBMHistoryDialog(dbmframe);

		}
		_history.setVisible(true);
	}
	/**
	 * [機 能] close History Dialog
	 * <p>
	 * [解 説] close History Dialog 。
	 * <p>
	 * 
	 * [備 考] なし
	 * 
	 * @param dbmframe
	 */
	public static synchronized void closeHistoryDialog() {
		if (_history == null) {
			return;
		} else {
			_history.setVisible(false);
			_history.dispose();
			return;
		}
	}

	/**
	 * [機 能] add History Entry
	 * <p>
	 * [解 説] add History Entry 。
	 * <p>
	 * 
	 * [備 考] なし
	 * 
	 * @param s
	 *            数据库类型
	 * @param s1
	 *            sql语句
	 */
	@SuppressWarnings("unchecked")
	public static synchronized void addHistoryEntry(String s, String s1) {
		// 添加History记录
		String s2 = DBMPropertiesUtil.getHistoryDirectory();
		File file = new File(s2);
		if (!file.exists()){
			try {
				file.mkdir();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());

		String s3 = String.valueOf(timestamp.getTime());
		String s4 = s3 + ".hdr";
		String s5 = s3 + ".sql";
		File file1 = new File(file, s4);
		File file2 = new File(file, s5);
		try {
			file1.createNewFile();
			file2.createNewFile();
			FileOutputStream fileoutputstream = new FileOutputStream(file1);
			FileOutputStream fileoutputstream1 = new FileOutputStream(file2);
			Properties properties = new Properties();
			properties.setProperty("CONNECTION_TYPE", s);

			properties.setProperty("STMT_TIME", s3);
			properties.store(fileoutputstream, "");
			fileoutputstream1.write(s1.getBytes());
			fileoutputstream.close();
			fileoutputstream1.close();
		} catch (Exception exception) {
			exception.printStackTrace();
			return;
		}
		ArrayList arraylist = new ArrayList();
		arraylist.add(timestamp);
		arraylist.add(s);
		arraylist.add(s1);
		_model.insertElementAt(arraylist, 0);
		if (getMaxHistory() != -1 && _model.getSize() > getMaxHistory()) {
			@SuppressWarnings("unused")
			int j = _model.getSize() - getMaxHistory();
			for (; _model.getSize() > getMaxHistory(); removeHistoryEntry(_model
					.getSize() - 1))
				;
		}
	}
	/**
	 * [機 能] load History From Disk
	 * <p>
	 * [解 説] load History From Disk 。
	 * <p>
	 * 
	 * [備 考] なし
	 */
	@SuppressWarnings("unchecked")
	public static synchronized void loadHistoryFromDisk() {
		// 从文件中读取记录
		String s = DBMPropertiesUtil.getHistoryDirectory();
		File file = new File(s);
		if (!file.exists())
			return;
		File afile[] = file.listFiles();
		TreeSet treeset = new TreeSet(new LongComparator());
		if (afile != null) {
			for (int i = 0; i < afile.length; i++) {
				File file1 = afile[i];
				if (file1 == null || !file1.getName().endsWith(".hdr"))
					continue;
				Long long2 = null;
				try {
					String s1 = file1.getName().substring(0,
							file1.getName().length() - 4);
					long2 = Long.valueOf(s1);
				} catch (Exception exception) {
					long2 = null;
				}
				if (long2 != null)
					treeset.add(long2);
			}

		}
		Iterator iterator = treeset.iterator();
		do {
			if (!iterator.hasNext())
				break;
			boolean flag = false;
			Long long1 = (Long) iterator.next();
			File file2 = new File(file, long1.toString() + ".hdr");
			String s2 = "";
			@SuppressWarnings("unused")
			String s3 = "";
			String s4 = "";
			Properties properties = new Properties();
			@SuppressWarnings("unused")
			Object obj1 = null;
			try {
				FileInputStream fileinputstream = new FileInputStream(file2);
				properties.load(fileinputstream);
				fileinputstream.close();
			} catch (Exception exception1) {
				flag = true;
			}
			s2 = properties.getProperty("CONNECTION_TYPE", "");

			s4 = properties.getProperty("STMT_TIME", "");
			long l = 0L;

			try {

				l = Long.valueOf(s4).longValue();
			} catch (Exception exception2) {
				flag = true;
			}
			File file3 = new File(file, long1 + ".sql");
			long l1 = file3.length();
			byte abyte0[] = new byte[(int) l1];
			try {
				FileInputStream fileinputstream1 = new FileInputStream(file3);
				fileinputstream1.read(abyte0);
				fileinputstream1.close();
			} catch (IOException ioexception) {
				flag = true;
			}
			String s5 = new String(abyte0);
			if (!flag) {
				ArrayList arraylist = new ArrayList();
				arraylist.add(new Timestamp(l));
				arraylist.add(s2);

				arraylist.add(s5);
				_model.insertElementAt(arraylist, 0);
			}
		} while (true);
	}
	/**
	 * [機 能] 删除i行记录
	 * <p>
	 * [解 説] 删除i行记录 。
	 * <p>
	 * @param i
	 * [備 考] なし
	 */
	public static synchronized void removeHistoryEntry(int i) {
		ArrayList arraylist = (ArrayList) _model.removeElementAt(i);
		Timestamp timestamp = (Timestamp) arraylist.get(0);
		String s = String.valueOf(timestamp.getTime());
		String s1 = DBMPropertiesUtil.getHistoryDirectory();
		File file = new File(s1);
		File file1 = new File(file, s + ".hdr");
		File file2 = new File(file, s + ".sql");
		file1.delete();
		file2.delete();
	}
	/**
	 * [機 能] 清空History
	 * <p>
	 * [解 説] 清空History 。
	 * <p>
	 * [備 考] なし
	 */
	public static synchronized void clearHistory() {
		int i = _model.getSize();
		for (int j = i - 1; j >= 0; j--)
			removeHistoryEntry(j);

	}
	/**
	 * [機 能] get History
	 * <p>
	 * [解 説] get History 。
	 * <p>
	 * @return _model
	 * [備 考] なし
	 */
	public static synchronized ArrayListTableModel getHistory() {
		return _model;
	}
	/**
	 * [機 能] 取得 History的最多数
	 * <p>
	 * [解 説] 取得 History的最多数 。
	 * <p>
	 * @return i
	 * [備 考] なし
	 */
	public static int getMaxHistory() {
		int i = -1;
		Properties properties = DBMPropertiesUtil.getUIProperties();
		String s = properties.getProperty("history.maxHistory", "5");
		try {
			i = Integer.parseInt(s);
		} catch (Exception exception) {
			i = 200;
		}
		return i;
	}

}