package mybatis.demo;

import java.util.Scanner;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {

	public static void main(String[] args) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);
		Scanner scanner = new Scanner(System.in);
		System.out.print("input password:");
		String password = scanner.nextLine();
		System.out.println(bCryptPasswordEncoder.encode(password));
		

	}

}
