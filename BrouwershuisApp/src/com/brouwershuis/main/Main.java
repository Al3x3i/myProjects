package com.brouwershuis.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.brouwershuis.db.model.Contract;
import com.brouwershuis.db.model.Employee;
import com.brouwershuis.db.model.EnumContract;
import com.brouwershuis.db.model.EnumRoles;
import com.brouwershuis.db.model.EnumShiftType;
import com.brouwershuis.db.model.Role;
import com.brouwershuis.db.model.Shift;
import com.brouwershuis.db.model.User;
import com.brouwershuis.helper.Helper;
import com.brouwershuis.pojo.EmloyeePojo;
import com.brouwershuis.service.ContractService;
import com.brouwershuis.service.EmployeeService;

public class Main {

	public Main() throws IOException {

		String address = "C:/Users/Al3x3i/Documents/backup/BrouwershuisApp/WebContent/WEB-INF/appconfig-root.xml";

		// Load all beans
		ApplicationContext context = new ClassPathXmlApplicationContext("file:" + address);
		AutowireCapableBeanFactory acbFactory = context.getAutowireCapableBeanFactory();
		acbFactory.autowireBean(this);
	}

	public static void main(String[] args) {

		try {
			System.out.println("Started EJB proect");

			Main p = new Main();
			p.start(args);

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	@Autowired
	EmployeeService empService;

	@Autowired
	ContractService contractService;

	private void start(String[] args) throws ParseException {

		//System.out.println("Started inserting default data");

//		int year = 2016;
//
//		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
//		calendar.set(year, 0, 1, 0, 0, 0);
//
//		int dayOfTheWeek = calendar.get(Calendar.DAY_OF_WEEK);
//		if(dayOfTheWeek > 5){
//			calendar.add(Calendar.DAY_OF_WEEK, 7 - dayOfTheWeek+ 1);
//		}else{
//			calendar.add(Calendar.DAY_OF_WEEK, - dayOfTheWeek + 1);
//		}
//		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
//		Date firstDate = calendar.getTime();
//
//
//		calendar.set(year, 11, 31, 0, 0, 0);
//		dayOfTheWeek = calendar.get(Calendar.DAY_OF_WEEK);
//		
//		if(dayOfTheWeek >= 5){
//			calendar.add(Calendar.DAY_OF_WEEK, 7 - dayOfTheWeek);
//		}else{
//			calendar.add(Calendar.DAY_OF_WEEK, - dayOfTheWeek );
//		}
//
//		Date lastDate = calendar.getTime();
//		System.out.println(year);
//		System.out.println("START:   " +firstDate.toString());
//		System.out.println("END:   " +lastDate.toString());

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("BrouwershuisApp");
		EntityManager entitymanager = emfactory.createEntityManager();

		entitymanager.getTransaction().begin();
		cleanDataFromAllTables(entitymanager);
		entitymanager.getTransaction().commit();

		entitymanager.getTransaction().begin();
		initRoleTable(entitymanager);
		entitymanager.getTransaction().commit();

		entitymanager.getTransaction().begin();
		initContractTable(entitymanager);
		entitymanager.getTransaction().commit();

		entitymanager.getTransaction().begin();
		initShiftTable(entitymanager);
		entitymanager.getTransaction().commit();

		// Init default employee with user to access to the web site
		initUserTable();

		// create dump employees
		entitymanager.getTransaction().begin();
		initEmployeeTable(entitymanager);

		entitymanager.getTransaction().commit();

		// Execute SQL file
		ExecuteSQLFile();

		System.out.println("Finished inserting default data");
	}

	private void ExecuteSQLFile() {

		try {
			// File testFile = new File("");
			// String currentPath = testFile.getAbsolutePath();
			// System.out.println("current path is: " + currentPath);

			FileReader f = new FileReader("src/com/brouwershuis/main/functions_procedures.sql");
			BufferedReader br = new BufferedReader(f);
			// StringBuilder sb = new StringBuilder();

			List<String> lines = new ArrayList<String>();
			String line;
			while ((line = br.readLine()) != null) {
				lines.add(line);
				// sb.append(line);
				lines.add(System.getProperty("line.separator"));
			}
			
			br.close();
			
			String sqlScript = String.join("", lines);

			String URL = "jdbc:mysql://localhost:3306/brouwerhuisdb?allowMultiQueries=true";
			String psw = "";
			String l = "root";

			Connection conn = DriverManager.getConnection(URL, l, psw);
			Statement stmt = conn.createStatement();

			stmt.execute(sqlScript);

			System.out.println("Executed sql script from file");

			conn.close();
		}

		catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	private static void cleanDataFromAllTables(EntityManager entitymanager) {

		Query q1 = entitymanager.createNativeQuery("DELETE FROM user_role");
		q1.executeUpdate();
		q1 = entitymanager.createNativeQuery("ALTER TABLE user_role AUTO_INCREMENT = 1");
		q1.executeUpdate();
		entitymanager.flush();

		q1 = entitymanager.createNativeQuery("DELETE FROM user");
		q1.executeUpdate();
		q1 = entitymanager.createNativeQuery("ALTER TABLE user AUTO_INCREMENT = 1");
		q1.executeUpdate();
		entitymanager.flush();

		q1 = entitymanager.createNativeQuery("DELETE FROM work_schedule");
		q1.executeUpdate();
		q1 = entitymanager.createNativeQuery("ALTER TABLE work_schedule AUTO_INCREMENT = 1");
		q1.executeUpdate();
		entitymanager.flush();

		q1 = entitymanager.createNativeQuery("DELETE FROM contract_hours");
		q1.executeUpdate();
		q1 = entitymanager.createNativeQuery("ALTER TABLE contract_hours AUTO_INCREMENT = 1");
		q1.executeUpdate();
		entitymanager.flush();
		
		q1 = entitymanager.createNativeQuery("DELETE FROM working_hours_record;");
		q1.executeUpdate();
		q1 = entitymanager.createNativeQuery("ALTER TABLE working_hours_record AUTO_INCREMENT = 1");
		q1.executeUpdate();
		entitymanager.flush();

		q1 = entitymanager.createNativeQuery("DELETE FROM employee;");
		q1.executeUpdate();
		q1 = entitymanager.createNativeQuery("ALTER TABLE employee AUTO_INCREMENT = 1");
		q1.executeUpdate();
		entitymanager.flush();

		q1 = entitymanager.createNativeQuery("DELETE FROM role;");
		q1.executeUpdate();
		q1 = entitymanager.createNativeQuery("ALTER TABLE role AUTO_INCREMENT = 1");
		q1.executeUpdate();
		entitymanager.flush();

		q1 = entitymanager.createNativeQuery("DELETE FROM contract;");
		q1.executeUpdate();
		q1 = entitymanager.createNativeQuery("ALTER TABLE contract AUTO_INCREMENT = 1");
		q1.executeUpdate();
		entitymanager.flush();

		q1 = entitymanager.createNativeQuery("DELETE FROM shift;");
		q1.executeUpdate();
		q1 = entitymanager.createNativeQuery("ALTER TABLE shift AUTO_INCREMENT = 1");
		q1.executeUpdate();
		entitymanager.flush();
	}

	private void initUserTable() throws ParseException {

		EmloyeePojo newEmployee = new EmloyeePojo();
		newEmployee.setAddress("test");
		newEmployee.setGender("Male");
		newEmployee.setAdmin(true);
		newEmployee.setEmployeeActive(true);
		newEmployee.setDateOfBirth("2000-01-01");
		newEmployee.setFirstName("test");
		newEmployee.setLastName("test");
		int contractId = contractService.findByName(EnumContract.CONTRACT_MEDEWERKER.toString()).getId();
		newEmployee.setContract(String.valueOf(contractId));

		newEmployee.setUsername("testing");
		newEmployee.setPassword("test");
		empService.addEmployee(newEmployee);

		newEmployee = new EmloyeePojo();
		newEmployee.setAddress("A");
		newEmployee.setGender("Female");
		newEmployee.setAdmin(false);
		newEmployee.setEmployeeActive(true);
		newEmployee.setDateOfBirth("2002-01-01");
		newEmployee.setFirstName("demo");
		newEmployee.setLastName("demo");
		contractId = contractService.findByName(EnumContract.CONTRACT_MEDEWERKER.toString()).getId();
		newEmployee.setContract(String.valueOf(contractId));

		newEmployee.setUsername("testing2");
		newEmployee.setPassword("test");
		empService.addEmployee(newEmployee);

	}

	private void initRoleTable(EntityManager entitymanager) {

		List<EnumRoles> allRoles = Arrays.asList(EnumRoles.values());
		Collections.sort(allRoles);

		for (EnumRoles enumRole : allRoles) {
			Role r = new Role();
			r.setName(enumRole.toString());
			entitymanager.persist(r);
		}
	}

	private void initContractTable(EntityManager entitymanager) {

		List<EnumContract> allContracts = Arrays.asList(EnumContract.values());
		Collections.sort(allContracts);

		for (EnumContract enumContract : allContracts) {
			Contract c = new Contract();
			c.setName(enumContract.toString());
			entitymanager.persist(c);
		}
	}

	private void initShiftTable(EntityManager entitymanager) {

		List<EnumShiftType> shiftTypes = Arrays.asList(EnumShiftType.values());
		Collections.sort(shiftTypes);

		for (EnumShiftType enumShiftType : shiftTypes) {
			Shift s = new Shift();
			s.setName(enumShiftType.toString());
			entitymanager.persist(s);
		}
	}

	private void initEmployeeTable(EntityManager entitymanager) throws ParseException {

		EmloyeePojo newEmployee = new EmloyeePojo();
		newEmployee.setAddress("Hello Address");
		newEmployee.setGender("Male");
		newEmployee.setAdmin(true);
		newEmployee.setEmployeeActive(true);
		newEmployee.setDateOfBirth("1990-01-01");
		newEmployee.setFirstName("Rob");
		newEmployee.setLastName("Mask");
		int contractId = contractService.findByName(EnumContract.CONTRACT_MEDEWERKER.toString()).getId();
		newEmployee.setContract(String.valueOf(contractId));

		empService.addEmployee(newEmployee);

		newEmployee = new EmloyeePojo();
		newEmployee.setAddress("Hello Address");
		newEmployee.setGender("Female");
		newEmployee.setAdmin(true);
		newEmployee.setEmployeeActive(true);
		newEmployee.setDateOfBirth("1990-01-01");
		newEmployee.setFirstName("Silvia");
		newEmployee.setLastName("Nekto");
		contractId = contractService.findByName(EnumContract.CONTRACT_CLIENT.toString()).getId();
		newEmployee.setContract(String.valueOf(contractId));

		empService.addEmployee(newEmployee);

		newEmployee = new EmloyeePojo();
		newEmployee.setAddress("Hello Address");
		newEmployee.setGender("Female");
		newEmployee.setAdmin(true);
		newEmployee.setEmployeeActive(true);
		newEmployee.setDateOfBirth("1990-01-01");
		newEmployee.setFirstName("Tom");
		newEmployee.setLastName("Saaremaa");
		contractId = contractService.findByName(EnumContract.CONTRACT_VRIJWILLIGER.toString()).getId();
		newEmployee.setContract(String.valueOf(contractId));

		empService.addEmployee(newEmployee);
	}
}
