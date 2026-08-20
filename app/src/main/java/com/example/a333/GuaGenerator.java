package com.example.a333;

import java.security.SecureRandom;
import java.util.*;
public class GuaGenerator {
    private static final SecureRandom RANDOM = new SecureRandom();

    // 八卦名称（索引：0=坤,1=艮,2=坎,3=巽,4=震,5=离,6=兑,7=乾）
    private static final String[] TRIGRAM_NAMES = {"坤", "艮", "坎", "巽", "震", "离", "兑", "乾"};

    // 64卦查找矩阵（行=上卦，列=下卦）
    private static final String[][] GUA_MATRIX = {
            // 上卦: 坤(0)
            {"坤为地", "山地剥", "水地比", "风地观", "雷地豫", "火地晋", "泽地萃", "天地否"},
            // 上卦: 艮(1)
            {"地山谦", "艮为山", "水山蹇", "风山渐", "雷山小过", "火山旅", "泽山咸", "天山遁"},
            // 上卦: 坎(2)
            {"地水师", "山水蒙", "坎为水", "风水涣", "雷水解", "火水未济", "泽水困", "天水讼"},
            // 上卦: 巽(3)
            {"地风升", "山风蛊", "水风井", "巽为风", "雷风恒", "火风鼎", "泽风大过", "天风姤"},
            // 上卦: 震(4)
            {"地雷复", "山雷颐", "水雷屯", "风雷益", "震为雷", "火雷噬嗑", "泽雷随", "天雷无妄"},
            // 上卦: 离(5)
            {"地火明夷", "山火贲", "水火既济", "风火家人", "雷火丰", "离为火", "泽火革", "天火同人"},
            // 上卦: 兑(6)
            {"地泽临", "山泽损", "水泽节", "风泽中孚", "雷泽归妹", "火泽睽", "兑为泽", "天泽履"},
            // 上卦: 乾(7)
            {"地天泰", "山天大畜", "水天需", "风天小畜", "雷天大壮", "火天大有", "泽天夬", "乾为天"}
    };
    // 动爻位置中文描述
    private static final String[] LINE_NAMES = {"初爻", "二爻", "三爻", "四爻", "五爻", "上爻"};
    /**
     * 核心函数：生成一次完整的卦象信息，返回描述字符串
     */
    public static String getGuaResult() {
        // 1. 摇6次，得到 6/7/8/9 数组（索引0=初爻）
        int[] yao = new int[6];
        for (int i = 0; i < 6; i++) {
            int faceCount = RANDOM.nextInt(2) + RANDOM.nextInt(2) + RANDOM.nextInt(2);
            switch (faceCount) {
                case 0: yao[i] = 6; break;  // 老阴
                case 1: yao[i] = 7; break;  // 少阳
                case 2: yao[i] = 8; break;  // 少阴
                case 3: yao[i] = 9; break;  // 老阳
            }
        }
        // 2. 计算本卦和变卦
        String benGua = getGuaName(yao);
        int[] changedYao = yao.clone();
        for (int i = 0; i < 6; i++) {
            if (yao[i] == 6) changedYao[i] = 7;  // 老阴→少阳
            else if (yao[i] == 9) changedYao[i] = 8; // 老阳→少阴
        }
        String bianGua = getGuaName(changedYao);
        // 3. 找出动爻位置
        List<String> movingLines = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            if (yao[i] == 6 || yao[i] == 9) {
                movingLines.add(LINE_NAMES[i]);
            }
        }
        String movingDesc = movingLines.isEmpty() ? "无动爻" : String.join("、", movingLines);

        // 4. 组合返回结果
        return String.format("本卦：%s，变卦：%s，动爻：%s", benGua, bianGua, movingDesc);
    }
    // 辅助方法：根据6个爻值（6/7/8/9）获取卦名
    private static String getGuaName(int[] yao) {
        int lowerIndex = getTrigramIndex(yao, 0); // 下卦（初二三）
        int upperIndex = getTrigramIndex(yao, 3); // 上卦（四五六）
        return GUA_MATRIX[lowerIndex][upperIndex];
    }
    // 将三个爻（6/7/8/9）转换为八卦索引（0~7）
    private static int getTrigramIndex(int[] yao, int start) {
        int idx = 0;
        for (int i = 0; i < 3; i++) {
            int value = yao[start + i];
            if (value == 7 || value == 9) {  // 阳爻
                idx |= (1 << (2 - i));  // 高位为下爻
            } // 阴爻保持0
        }
        return idx;
    }
    /**
     * 传入你已经摇好的6个爻（数组），自动计算本卦、变卦和动爻
     * @param yao 长度为6的数组，每个元素必须是 6(老阴)、7(少阳)、8(少阴)、9(老阳)
     *            索引0代表初爻（最底下），索引5代表上爻（最顶上）
     * @return 格式化的卦象描述字符串
     */
    public static String getGuaResult(int[] yao) {
        // 1. 简单校验数组合法性
        if (yao == null || yao.length != 6) {
            return "错误：数组必须包含6个整数";
        }
        for (int v : yao) {
            if (v < 6 || v > 9) {
                return "错误：每个元素必须为 6、7、8、9";
            }
        }

        // 2. 计算本卦
        String benGua = getGuaName(yao);

        // 3. 计算变卦（动爻变化）
        int[] changedYao = yao.clone();
        for (int i = 0; i < 6; i++) {
            if (yao[i] == 6) changedYao[i] = 7;  // 老阴→少阳
            else if (yao[i] == 9) changedYao[i] = 8; // 老阳→少阴
        }
        String bianGua = getGuaName(changedYao);

        // 4. 找动爻
        List<String> movingLines = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            if (yao[i] == 6 || yao[i] == 9) {
                movingLines.add(LINE_NAMES[i]);
            }
        }
        String movingDesc = movingLines.isEmpty() ? "无动爻" : String.join("、", movingLines);

        return String.format("本卦：%s，变卦：%s，动爻：%s", benGua, bianGua, movingDesc);
    }

}