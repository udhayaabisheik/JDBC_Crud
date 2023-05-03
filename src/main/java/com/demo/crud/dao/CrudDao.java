package com.demo.crud.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.UUID;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import com.demo.crud.model.CrudModel;
import com.demo.crud.model.Response;
import com.demo.crud.service.CrudService;

@Component
public class CrudDao implements CrudService {

	@Autowired
	private JavaMailSender javaMailSender;

	Response res = new Response();
	String url = "jdbc:mysql://127.0.0.1:3306/crud";
	String username = "root";
	String password = "qwerty@03";

	@Override
	public Response insertUser(CrudModel input) {
		String uuid = UUID.randomUUID().toString();
		Date date = new Date(Calendar.getInstance().getTime().getTime());
		input.setUpdatedDate(date);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection con = DriverManager.getConnection(url, username, password);
					Statement st = con.createStatement();) {
				String insertQuery = "INSERT INTO crud.personal_details(s_no,first_name,last_name,gender,age,email,password,phone_no,updated_date)VALUES('"
						+ uuid + "','" + input.getFirstName() + "','" + input.getLastName() + "','" + input.getGender()
						+ "','" + input.getAge() + "','" + input.getEmail() + "','" + input.getPassword() + "',"
						+ input.getPhoneNumber() + ",'" + input.getUpdatedDate() + "');";
				st.executeUpdate(insertQuery);
				res.setResponseCode(200);
				res.setResponseMsg("success");
				res.setData("User Created Successfully");
			} catch (Exception e) {
				e.printStackTrace();
				res.setResponseCode(500);
				res.setResponseMsg("error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Response getAllUser() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection con = DriverManager.getConnection(url, username, password);
					Statement st = con.createStatement();) {
				String getAllUserQuery = "select * from personal_details;";
				ResultSet rs = st.executeQuery(getAllUserQuery);
				JSONArray jsonArray = new JSONArray();
				while (rs.next()) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("sNo", rs.getString("s_no"));
					jsonObject.put("firstName", rs.getString("first_name"));
					jsonObject.put("lastName", rs.getString("last_name"));
					jsonObject.put("gender", rs.getString("gender"));
					jsonObject.put("age", rs.getInt("age"));
					jsonObject.put("phoneNumber", rs.getLong("phone_no"));
					jsonObject.put("email", rs.getString("email"));
					jsonObject.put("password", rs.getString("password"));
					jsonObject.put("updatedDate", rs.getDate("updated_date"));

					jsonArray.add(jsonObject);
				}

				res.setResponseCode(200);
				res.setResponseMsg("success");
				res.setjData(jsonArray);
			} catch (Exception e) {
				e.printStackTrace();
				res.setResponseCode(500);
				res.setResponseMsg("error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Response getUserById(String id) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection con = DriverManager.getConnection(url, username, password);
					Statement st = con.createStatement();) {
				String getUserByIdQuery = "select * from personal_details where s_no = '" + id + "';";
				ResultSet rs = st.executeQuery(getUserByIdQuery);
				JSONObject jsonObject = new JSONObject();
				while (rs.next()) {

					jsonObject.put("sNo", rs.getString("s_no"));
					jsonObject.put("firstName", rs.getString("first_name"));
					jsonObject.put("lastName", rs.getString("last_name"));
					jsonObject.put("gender", rs.getString("gender"));
					jsonObject.put("phoneNumber", rs.getLong("phone_no"));
					jsonObject.put("email", rs.getString("email"));
					jsonObject.put("password", rs.getString("password"));
					jsonObject.put("updatedDate", rs.getDate("updated_date"));

				}

				res.setResponseCode(200);
				res.setResponseMsg("success");
				res.setjData(jsonObject);
			} catch (Exception e) {
				e.printStackTrace();
				res.setResponseCode(500);
				res.setResponseMsg("error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public Response updateUserDetail(String email, String id) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection con = DriverManager.getConnection(url, username, password);
					Statement st = con.createStatement();) {
				String updateQueryString = "update personal_details set email= '" + email + "' where s_no = '" + id
						+ "'";
				st.executeUpdate(updateQueryString);
				res.setResponseCode(200);
				res.setResponseMsg("success");
			} catch (Exception e) {
				e.printStackTrace();
				res.setResponseCode(500);
				res.setResponseMsg("error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public Response deleteUser(String id) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection con = DriverManager.getConnection(url, username, password);
					Statement st = con.createStatement();) {
				String deleteUserString = "delete from personal_details where s_no = '" + id + "';";
				st.executeUpdate(deleteUserString);
				res.setResponseCode(200);
				res.setResponseMsg("success");
			} catch (Exception e) {
				e.printStackTrace();
				res.setResponseCode(500);
				res.setResponseMsg("error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public Response sendEmail(CrudModel emailProcess) {
		try {
			String fromEmail = "visit.designer03@gmail.com";
			String sendText = "Greetings, \n \tName of the person: " + emailProcess.getPersonName()
					+ "\n \tEmail of the person: " + emailProcess.getPersonEmail() + "\n \tMessage from the person : "
					+ emailProcess.getPersonMessage() + "\n \n Thanks & regards,\n \n"+emailProcess.getPersonName();
			String sendSubject = "RE: User Response From Portfolio";

			SimpleMailMessage smm = new SimpleMailMessage();
			smm.setFrom(fromEmail);
			smm.setTo(fromEmail);
			smm.setSubject(sendSubject);
			smm.setText(sendText);
			javaMailSender.send(smm);
			
			res.setResponseCode(200);
			res.setResponseMsg("success");
		} catch (Exception e) {
			e.printStackTrace();
			res.setResponseCode(500);
			res.setResponseMsg("error");
		}
		return res;
	}

}
