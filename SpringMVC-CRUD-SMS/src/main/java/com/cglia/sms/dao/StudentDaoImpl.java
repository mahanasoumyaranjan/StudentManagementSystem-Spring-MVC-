package com.cglia.sms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cglia.sms.dbutil.DatabaseConnection;
import com.cglia.sms.model.Student;

@Repository
public class StudentDaoImpl implements StudentDao {

	@Override
	public Integer save(Student student) {
		final String query = "INSERT INTO student_info (NAME, EMAIL, DEPARTMENT) values (?,?,?)";
		Integer id = 0;
		try (Connection con = DatabaseConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {

			ps.setString(1, student.getName());
			ps.setString(2, student.getEmail());
			ps.setString(3, student.getDept());

			int count = ps.executeUpdate();
			if (count != 0) {
				try (ResultSet rs = ps.getGeneratedKeys()) {
					if (rs.next()) {
						id = rs.getInt(1);
						System.out.println("Student saved with id=" + id);
					}
				}
			} else {
				System.out.println("Failed to add student!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public Student getById(Integer id) {
		final String query = "SELECT * FROM student_info WHERE ID = ?";
		Student std = null;
		try (Connection con = DatabaseConnection.getConnection();
				PreparedStatement stmt = con.prepareStatement(query)) {
			stmt.setInt(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					std = new Student();
					std.setId(rs.getInt("ID"));
					std.setName(rs.getString("NAME"));
					std.setEmail(rs.getString("EMAIL"));
					std.setDept(rs.getString("DEPARTMENT"));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return std;
	}

	@Override
	public int update(Student student) {
		final String query = "UPDATE student_info SET NAME = ?,  EMAIL = ?, DEPARTMENT = ? WHERE ID = ?";
		int count = 0;
		try (Connection con = DatabaseConnection.getConnection();
				PreparedStatement stmt = con.prepareStatement(query)) {
			stmt.setString(1, student.getName());
			stmt.setString(2, student.getEmail());
			stmt.setString(3, student.getDept());
			stmt.setDouble(4, student.getId());

			count = stmt.executeUpdate();
			if (count != 0) {
				System.out.println("Student with ID:" + student.getId() + " is updated");
			} else {
				System.out.println("Failed to update student data!");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int deleteById(Integer id) {
		final String query = "DELETE FROM student_info WHERE id=?";
		int count = 0;
		try (Connection con = DatabaseConnection.getConnection();
				PreparedStatement stmt = con.prepareStatement(query);) {
			stmt.setInt(1, id);
			count = stmt.executeUpdate();

			if (count != 0) {
				System.out.println("Student with ID:" + id + " is deleted");
			} else {
				System.out.println("Failed to delete record!");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List<Student> getAll() {
		final String query = "SELECT * FROM student_info";
		List<Student> stdList = new ArrayList<>();

		try (Connection con = DatabaseConnection.getConnection(); Statement stmt = con.createStatement();) {
			boolean areAnyRecords = stmt.execute(query);
			if (areAnyRecords) {
				try (ResultSet rs = stmt.getResultSet()) {
					if (rs != null) {
						while (rs.next()) {
							Student std = new Student();
							std.setId(rs.getInt("ID"));
							std.setName(rs.getString(2));
							std.setEmail(rs.getString(3));
							std.setDept(rs.getString(4));

							stdList.add(std);
						}
					}
				}
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stdList;
	}

}
