package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import common.ComUtil;

public class DecodeStringToFile {

    /** 共通処理クラス */
    private static ComUtil cu = null;
    /** インスタンス  */
    private static DecodeStringToFile ins = null;

    public static void main(String[] args) {

        // 入力ファイル
        String inputFile = null;
        // 出力フォルダ
        String outputDir = null;
        // ファイルリスト
        File fileList[] = null;

        // エラーフラグ
        boolean errFlg = false;

        // インスタンス生成
        ins = new DecodeStringToFile();
        // 共通処理クラスインスタンス化
        cu = new ComUtil();

        // 引数チェック
        if ( args.length != 1 ) {
            System.out.println("パラメータの数が間違っています。");
            System.exit(1);
        }

        // 入力ファイル取得
        inputFile = args[0];

        try {
            if ( new File(inputFile).isDirectory() ) {
                // 対象がディレクトリの場合
                // 出力ファイル取得
                outputDir = inputFile;
                if ( !outputDir.endsWith(File.separator) ) {
                    outputDir = outputDir + File.separator;
                }
                // ファイル一覧取得
                fileList = new File(inputFile).listFiles();
                for ( File file : fileList ) {
                    // エンコードメソッド呼び出し
                    if ( !ins.decode(file, outputDir) ) {
                        // エラーフラグON
                        errFlg = true;
                    }
                }
            } else {
                // 対象がファイルの場合
                // 出力ファイル取得
                outputDir = new File(inputFile).getParent();
                if ( !outputDir.endsWith(File.separator) ) {
                    outputDir = outputDir + File.separator;
                }
                // エンコードメソッド呼び出し
                if ( !ins.decode(new File(inputFile), outputDir) ) {
                    // エラーフラグON
                    errFlg = true;
                }
            }
        } catch ( IOException ie ) {

        } catch ( Exception e ) {
            System.out.println("例外が発生しました。");
            e.printStackTrace();
            System.exit(1);
        }

        if ( errFlg ) {
            System.out.println("一部のファイルの変換に失敗しました。");
            System.exit(1);
        } else {
            System.out.println("正常に変換が完了しました。");
            System.exit(0);
        }

    }

    /**
     * デコードメソッド
     * @param aFile デコード対象データ記載ファイル
     * @param anOutputPath 出力フォルダパス
     * @return true：成功/false：失敗
     * @throws IOException
     */
    private boolean decode(File aFile, String anOutputPath) throws IOException {
        // 戻り値
        boolean res = true;
        // ファイル読み込みクラス
        BufferedReader  input = null;
        // 出力ファイル名
        String fileName = null;
        // 書き込みデータ
        String data = null;
        //
        Matcher m = null;
        // 書き込みクラス
        FileOutputStream output = null;

        try {
            System.out.println("デコード開始：" + aFile.getName());
            // 変換元データ取得
            input = new BufferedReader(new FileReader(aFile));

            // ファイル読み込み
            // ファイル名取得
            fileName = input.readLine().split("：")[1];
            // 書き込みデータ取得
            data = input.readLine();

            // ファイルクローズ
            input.close();

            // 書き込み用ファイル取得
            output = new FileOutputStream(anOutputPath + fileName);

            m = Pattern.compile("[0-9a-zA-Z]{2}").matcher(data);
            while ( m.find() ) {
                // データ書き込み
                output.write(cu.exchangeHexToDec(m.group()));
            }

            // ファイルクローズ
            output.close();

        } catch (IOException ie) {
            ie.printStackTrace();
            res = false;
        } catch (Exception e) {
            e.printStackTrace();
            res = false;
        } finally {
            try {
                if ( input != null ) {
                    input.close();
                }
                if ( output != null ) {
                    output.close();
                }
            } catch (IOException ie) {
                System.out.println("ファイルクローズでエラーが発生しました。：" + aFile.getName());
            }
        }
        return res;
    }

}
