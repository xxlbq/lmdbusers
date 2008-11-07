/**
 * $Id: FinderCreator.java,v 1.4 2006/11/13 05:47:53 lijc Exp $ 
 * 在编辑器中查找文本
 */
package com.livedoor.dbm.components.queryanalyzer.find;

/**
 * <p> Title: 查找实现类 </p> 
 * <p> Description: 各种查找实现类 </p> 
 * <p> Copyright: Copyright (c) 2006 </p> 
 * <p> Company: 英極軟件開發（大連）有限公司 </p>
 * 
 * @author <a href="mailto:lijc@livedoor.cn">LiJicheng</a>
 * @version 1.0
 */
public class FinderCreator {

    /**
     * 构造正常查找类, 根据查找选项构造合适的查找类
     * 
     * @param findOption
     * @return
     */
    public StringFinder createFinder(FindOption findOption) {
        String findWhat = findOption.getFindWhat();
        if (findWhat == null || findWhat.length() == 0)
            return new FalseFinder();

        boolean matchCase = findOption.isMatchCase();
        if (findOption.isWholeWords()) {
            if (findOption.isBwdSearch()) { // 全字匹配向后查找
                return new WholeWordsBwdFinder(findWhat, matchCase);
            } else {// 全字匹配向前查找
                return new WholeWordsFwdFinder(findWhat, matchCase);
            }
        } else {
            if (findOption.isBwdSearch()) {// 向后查找
                return new StringBwdFinder(findWhat, matchCase);
            } else {// 向前查找
                return new StringFwdFinder(findWhat, matchCase);
            }
        }
    }

    /**
     * Finder接口抽象实现,派生类只有find()方法需要实现
     */
    public static abstract class AbstractFinder implements Finder {

        /**
         * 是否查找到
         */
        protected boolean found;

        /**
         * 是否查找到
         */
        public final boolean isFound() {
            return found;
        }

        /**
         * 复位
         */
        public void reset() {
            found = false;
        }

    }

    public static class TrueFinder extends AbstractFinder {

        public int find(int bufferStartPos, char buffer[], int offset1, int offset2, int reqPos, int limitPos) {
            found = true;
            return reqPos;
        }

    }

    public static class FalseFinder extends AbstractFinder implements StringFinder {

        public int find(int bufferStartPos, char buffer[], int offset1, int offset2, int reqPos, int limitPos) {
            return -1;
        }

        public int getFoundLength() {
            return 0;
        }

    }

    /**
     * 向前查找
     */
    public static abstract class GenericFwdFinder extends AbstractFinder {

        public final int find(int bufferStartPos, char buffer[], int offset1, int offset2, int reqPos, int limitPos) {
            int offset = reqPos - bufferStartPos;
            int limitOffset = limitPos - bufferStartPos - 1;
            while (offset >= offset1 && offset < offset2) {
                offset += scan(buffer[offset], (offset == limitOffset));
                if (found) {
                    break;
                }
            }
            return bufferStartPos + offset;
        }

        /**
         * 此方法决定是否找到期待的字符串,方法接收当前字符及是否最后一个字符标志
         * 
         * @return 如果找到了期待的字符串,设置found为true并返回回退字符数以回到查找字符串的开始位置;
         *         如果没找到,返回缓冲区向前移动字符数,这种情况应该始终为1
         */
        protected abstract int scan(char ch, boolean lastChar);

    }

    /**
     * 向后查找
     */
    public static abstract class GenericBwdFinder extends AbstractFinder {

        public final int find(int bufferStartPos, char buffer[], int offset1, int offset2, int reqPos, int limitPos) {
            int offset = reqPos - bufferStartPos;
            int limitOffset = limitPos - bufferStartPos;
            while (offset >= offset1 && offset < offset2) {
                offset += scan(buffer[offset], (offset == limitOffset));
                if (found) {
                    break;
                }
            }
            return bufferStartPos + offset;
        }

        /**
         * 此方法决定是否找到期待的字符串,方法接收当前字符及是否最后一个字符标志
         * 
         * @return 如果找到了期待的字符串,设置found为true并返回回退字符数以回到查找字符串的开始位置(反方向),0表示当前字符;
         *         如果没找到,返回缓冲区向前移动字符数,这种情况应该始终为-1
         */
        protected abstract int scan(char ch, boolean lastChar);

    }

    /**
     * 向前查找
     */
    public static final class StringFwdFinder extends GenericFwdFinder implements StringFinder {

        char chars[];

        int stringInd;

        boolean matchCase;

        public StringFwdFinder(String s, boolean matchCase) {
            this.matchCase = matchCase;
            chars = (matchCase ? s : s.toLowerCase()).toCharArray();
        }

        public int getFoundLength() {
            return chars.length;
        }

        public void reset() {
            super.reset();
            stringInd = 0;
        }

        protected int scan(char ch, boolean lastChar) {
            if (!matchCase) {
                ch = Character.toLowerCase(ch);
            }
            if (ch == chars[stringInd]) {
                stringInd++;
                if (stringInd == chars.length) { // 找到查找的字符串
                    found = true;
                    return 1 - stringInd; // 回退字符数
                }

                return 1; // 找到,去缓冲区下个字符
            } else { //没找到
                if (stringInd == 0) {
                    return 1;
                } else {
                    int back = 1 - stringInd;
                    stringInd = 0;
                    return back;
                }
            }
        }
    }

    /**
     * 向后查找
     */
    public static class StringBwdFinder extends GenericBwdFinder implements StringFinder {

        char chars[];

        int stringInd;

        boolean matchCase;

        int endInd;

        public StringBwdFinder(String s, boolean matchCase) {
            this.matchCase = matchCase;
            chars = (matchCase ? s : s.toLowerCase()).toCharArray();
            endInd = chars.length - 1;
        }

        public int getFoundLength() {
            return chars.length;
        }

        public void reset() {
            super.reset();
            stringInd = endInd;
        }

        protected int scan(char ch, boolean lastChar) {
            if (!matchCase) {
                ch = Character.toLowerCase(ch);
            }
            if (ch == chars[stringInd]) {
                stringInd--;
                if (stringInd == -1) {
                    found = true;
                    return 0;
                }
                return -1;
            } else {
                if (stringInd == endInd) {
                    return -1;
                } else {    // 回退查找过的字符个数-2个字符
                    int back = chars.length - 2 - stringInd;
                    stringInd = endInd;
                    return back;
                }
            }
        }
    }

    /**
     * 全字向前查找
     * 
     * @author lijicheng
     */
    public static final class WholeWordsFwdFinder extends GenericFwdFinder implements StringFinder {

        char chars[];

        int stringInd;

        boolean matchCase;

        boolean insideWord;

        boolean firstCharWordPart;

        boolean wordFound;

        public WholeWordsFwdFinder(String s, boolean matchCase) {
            this.matchCase = matchCase;
            chars = (matchCase ? s : s.toLowerCase()).toCharArray();
            firstCharWordPart = Character.isJavaIdentifierPart(chars[0]);
        }

        public int getFoundLength() {
            return chars.length;
        }

        public void reset() {
            super.reset();
            insideWord = false;
            wordFound = false;
            stringInd = 0;
        }

        protected int scan(char ch, boolean lastChar) {
            if (!matchCase) {
                ch = Character.toLowerCase(ch);
            }

            if (wordFound) {
                if (Character.isJavaIdentifierPart(ch)) { 
                    wordFound = false;
                    insideWord = firstCharWordPart;
                    stringInd = 0;
                    return 1 - chars.length;
                } else {
                    found = true;
                    return -chars.length;
                }
            }

            if (stringInd == 0) { 
                if (ch != chars[0] || insideWord) { 
                    insideWord = Character.isJavaIdentifierPart(ch);
                    return 1;
                } else { 
                    stringInd = 1; 
                    if (chars.length == 1) {
                        if (lastChar) {
                            found = true;
                            return 0;
                        } else {
                            wordFound = true;
                            return 1;
                        }
                    }
                    return 1;
                }
            } else { 
                if (ch == chars[stringInd]) { 
                    stringInd++;
                    if (stringInd == chars.length) { 
                        if (lastChar) {
                            found = true;
                            return 1 - chars.length; 
                        } else {
                            wordFound = true;
                            return 1;
                        }
                    }
                    return 1;
                } else { 
                    int back = 1 - stringInd;
                    stringInd = 0;
                    insideWord = firstCharWordPart;
                    return back; 
                }
            }
        }

    }

    /**
     * 全字向后查找
     */
    public static final class WholeWordsBwdFinder extends GenericBwdFinder implements StringFinder {

        char chars[];

        int stringInd;

        boolean matchCase;

        boolean insideWord;

        boolean lastCharWordPart;

        boolean wordFound;

        int endInd;

        public WholeWordsBwdFinder(String s, boolean matchCase) {
            this.matchCase = matchCase;
            chars = (matchCase ? s : s.toLowerCase()).toCharArray();
            endInd = chars.length - 1;
            Character.isJavaIdentifierPart(chars[endInd]);
        }

        public int getFoundLength() {
            return chars.length;
        }

        public void reset() {
            super.reset();
            insideWord = false;
            wordFound = false;
            stringInd = endInd;
        }

        protected int scan(char ch, boolean lastChar) {
            if (!matchCase) {
                ch = Character.toLowerCase(ch);
            }

            if (wordFound) {
                if (Character.isJavaIdentifierPart(ch)) {
                    wordFound = false;
                    insideWord = lastCharWordPart;
                    stringInd = endInd;
                    return endInd;
                } else {
                    found = true;
                    return 1;
                }
            }

            if (stringInd == endInd) {
                if (ch != chars[endInd] || insideWord) {
                    insideWord = Character.isJavaIdentifierPart(ch);
                    return -1;
                } else {
                    stringInd = endInd - 1; 
                    if (chars.length == 1) {
                        if (lastChar) {
                            found = true;
                            return 0;
                        } else {
                            wordFound = true;
                            return -1;
                        }
                    }
                    return -1;
                }
            } else {
                if (ch == chars[stringInd]) {
                    stringInd--;
                    if (stringInd == -1) {
                        if (lastChar) {
                            found = true;
                            return 0;
                        } else {
                            wordFound = true;
                            return -1;
                        }
                    }
                    return -1; 
                } else { 
                    int back = chars.length - 2 - stringInd;
                    stringInd = endInd;
                    insideWord = lastCharWordPart;
                    return back;
                }
            }
        }
    }

    /**
     * 字符串查找接口
     * 可以查找正常字符串,也可以按正则表达式匹配
     */
    public interface StringFinder extends Finder {

        /**
         * 返回查找字符串长度,正则匹配时有用
         */
        public int getFoundLength();

    }

}
