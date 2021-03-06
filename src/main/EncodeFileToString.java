package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import common.ComUtil;

public class EncodeFileToString {

    /** 出力先ファイル名パターン */
    private final String FileNamePattern = "yyyyMMddHHmm";

    /** 共通処理クラス */
    private static ComUtil cu = null;
    /** インスタンス  */
    private static EncodeFileToString ins = null;

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
        ins = new EncodeFileToString();
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
                    if ( !ins.encode(file, outputDir) ) {
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
                if ( !ins.encode(new File(inputFile), outputDir) ) {
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
     * エンコードメソッド
     * @param aFile エンコード対象ファイル
     * @param anOutputPath 出力フォルダパス
     * @return true：成功/false：失敗
     * @throws IOException
     */
    private boolean encode(File aFile, String anOutputPath) throws IOException {
        // 戻り値
        boolean res = true;
        // ファイル読み込みクラス
        FileInputStream input = null;
        // 読み込みデータ
        int readData = 0;
        // データリスト
        ArrayList<Integer> data = new ArrayList<Integer>();
        // 書き込みクラス
        PrintWriter output = null;

        try {
            System.out.println("エンコード開始：" + aFile.getName());
            // 変換対象ファイル取得
            input = new FileInputStream(aFile);
            // データ読み込み
            while ( (readData = input.read()) != -1 ) {
                // 読み込みデータ格納
                data.add(readData);
            }
            // ファイルクローズ
            input.close();

            // 書き込み用ファイル取得
            output = new PrintWriter(anOutputPath + ins.createFileName(aFile.getName()));
            output.println("ファイル名：" + aFile.getName());
            // データ書き込み
            for ( int i : data ) {
                output.print(cu.exchangeDecToHex(i));
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

    private String createFileName(String aFileName) {
        // 戻り値
        String res = null;
        res = new SimpleDateFormat(ins.FileNamePattern).format(new Date());
        res = res + "_" + cu.excludeExtension(aFileName) + ".txt";
        return res;
    }

}
