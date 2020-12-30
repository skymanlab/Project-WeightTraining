package com.skymanlab.weighttraining.management.user.data;

import com.skymanlab.weighttraining.management.developer.Display;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class EncryptionUtils {

    // constance
    private static final String CLASS_NAME = "[UD]_EncryptionUtils";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;


    /**
     * [method] 랜덤으로 만들어진 난수 SALT 값을 생성하여 반환한다.
     *
     * <p>
     * 단반향 해시 함수에서 다이제스트를 생성할 때 추가되는 바이트 단위의 임의의 문자열이다.
     * 그리고 이 원본 메시지에 문자열을 추가하여 다이제스를 생성하는 것을 솔팅이라고 한다.
     * </p>
     *
     * @return 난수로 만들어진 SALT
     */
    public static String generateSalt() {

        // [lv/C]Random :
        Random random = new Random();

        // [lv/b]salt :
        byte[] tempSalt = new byte[10];

        random.nextBytes(tempSalt);

        StringBuffer salt = new StringBuffer();

        // [cycle 1] : tempSalt 길이만큼
        for (int index = 0; index < tempSalt.length; index++) {

            salt.append(String.format("%02x", tempSalt[index]));

        } // [cycle 1]Salt converted to bytes

        return salt.toString();
    } // End of method [generateSalt]


    /**
     * [method] password 와 salt 로 해당 password 를 암호화하여 암호화된 password 를 반환한다.
     *
     * @param password 평문 password
     * @param salt     난수 salt
     * @return 암호화된 password
     */
    public static String encryptPassword(String password, String salt) {

        // [lv/C]String : 암호화된 결과를 담을 문자열 변수
        String encryptedPassword = "";

        // [lv/b]convertedPasswordToByte : 매개변수로 받은 password 를 byte[] 로 변환
        byte[] convertedPasswordToByte = password.getBytes();

        // [lv/b]convertedSaltToByte : 매개변수로 받은 salt 를 byte[] 로 변환
        byte[] convertedSaltToByte = salt.getBytes();

        // [lv/b] : byte 로 변환된 password 와 salt 의 길이로 배열 생성 및 하나의 byte 배열로 만들기
        byte[] bytes = new byte[convertedPasswordToByte.length + convertedSaltToByte.length];
        System.arraycopy(convertedPasswordToByte, 0, bytes, 0, convertedPasswordToByte.length);
        System.arraycopy(convertedSaltToByte, 0, bytes, convertedPasswordToByte.length, convertedSaltToByte.length);

        try {
            // [lv/C]MessageDigest : SHA-256 알고리즘으로 생성
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(bytes);

            // [lv/C]digestData :
            byte[] digestData = digest.digest();

            StringBuffer temp = new StringBuffer();

            // [cycle 1] : digestData 의 길이만큼
            for (int index = 0; index < digestData.length; index++) {

                // [lv/C]StringBuffer : digestData 를 암호화
                temp.append(Integer.toString((digestData[index] & 0xFF) + 256, 16).substring(1));

            } // [cycle 1]

            // [lv/C]String : encryptedPassword 에 암호화된 결과를 저장
            encryptedPassword = temp.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return encryptedPassword;
    } // End of method [encryptPassword]

}
