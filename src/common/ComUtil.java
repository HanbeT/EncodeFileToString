package common;

public class ComUtil {

    /**
     * 空文字チェックメソッド
     * @param aParam 処理対象
     * @return true：空文字/false：非空文字
     */
    public boolean isEmpty(String aParam) {
        return aParam == null || aParam.isEmpty();
    }

    /**
     * ファイル拡張子除外メソッド
     * @param aParam ファイル名
     * @return ファイル名(拡張子除外)
     */
    public String excludeExtension(String aParam) {
        return aParam.substring(0, aParam.lastIndexOf("."));
    }

    /**
     * 進数変換(10→16)メソッド
     * @param aParam 10進数
     * @return 16進数
     */
    public String exchangeDecToHex(int aParam) {
        String res = Integer.toHexString(aParam);
        if ( aParam < 16 ) {
            res = "0" + res;
        }
        return res;
    }

    /**
     * 進数変換(16→10)メソッド
     * @param aParam 16進数
     * @return 10進数
     */
    public int exchangeHexToDec(String aParam) {
        int res = Integer.parseInt(aParam, 16);
        return res;
    }
}
