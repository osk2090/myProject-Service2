package com.osk2090.util;

public class Agreement {
    public static boolean Agree() {
        if (Prompt.promptAgreeString("개인정보 수집 및 이용에 동의합니다.[Y/N]").equalsIgnoreCase("y")) {
            if (Prompt.promptAgreeString("당첨 되실 경우 당첨자 본인만 당첨 매장에 방문하여 수령 가능합니다.[Y/N]").equalsIgnoreCase("y")) {
                if (Prompt.promptAgreeString("당첨자는 본인 확인을 위해 신분증 및 핸드폰으로 받으신 SMS(원본)을 확인하오니 지참해주시기 바랍니다.[Y/N]").equalsIgnoreCase("y")) {
                    if (Prompt.promptAgreeString("당첨자는 공지 드리는 구매 기간 외에는 구매 불가하며, 카드 결제시 본인 명의 카드로만 결제가 가능합니다.[Y/N]").equalsIgnoreCase("y")) {
                        System.out.println("다음 진행");
                        return true;
                    }
                }
            }
        }
        return false;
    }
}