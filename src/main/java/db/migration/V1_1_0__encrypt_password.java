package db.migration;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class V1_1_0__encrypt_password extends BaseJavaMigration {
	@Override
	public void migrate(Context context) throws Exception {
		PasswordEncoder encoder = new BCryptPasswordEncoder();

		try (PreparedStatement users = context.getConnection()
				.prepareStatement("select * from users")) {
			try (ResultSet userList = users.executeQuery()) {
				while (userList.next()) {
					String clearPassword = userList.getString("password");
					byte[] id = userList.getBytes("id");

					try (PreparedStatement kullaniciSifreUpdate = context.getConnection()
							.prepareStatement("update users set password = ? where id = ?")) {
						kullaniciSifreUpdate.setString(1, encoder.encode(clearPassword));
						kullaniciSifreUpdate.setBytes(2, id);
						kullaniciSifreUpdate.execute();
					}
				}
			}
		}
	}
}
