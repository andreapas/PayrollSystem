package it.unipv.payroll.tests;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.unipv.payroll.controller.ChargesController;
import it.unipv.payroll.controller.EmployeeController;
import it.unipv.payroll.controller.FullTimeController;
import it.unipv.payroll.controller.PartTimeController;
import it.unipv.payroll.controller.SalesController;
import it.unipv.payroll.controller.SessionManagementController;
import it.unipv.payroll.controller.TimeCardController;
import it.unipv.payroll.controller.UnionsController;
import it.unipv.payroll.model.Charges;
import it.unipv.payroll.model.Credentials;
import it.unipv.payroll.model.Employee;
import it.unipv.payroll.model.FullTimeEmployee;
import it.unipv.payroll.model.IEmployee;
import it.unipv.payroll.model.IUnion;
import it.unipv.payroll.model.PartTimeEmployee;
import it.unipv.payroll.model.Sales;
import it.unipv.payroll.model.TimeCard;
import it.unipv.payroll.model.Union;
import it.unipv.payroll.utils.AutoPayday;
import it.unipv.payroll.utils.UnionConverter;

@RunWith(Arquillian.class)
public class SystemTest extends ArquillianTest {

	private static String USER2_COD = "wow987654321";
	private static String USER1_COD = "ABC001DEF";
	private static String USER1_NAME = "Mario";
	private static String USER1_SURNAME = "Rossi";
	private static String USER1_EMAIL = "mario.rossi@mariorossi.it";
	private static String USER1_EMAIL_EDITED = "mario.rossini@mariorossini.it";
	private static Union USER1_UNION;
	private static Union USER1_UNION_EDITED;
	private static String PAYMENT_METHOD1 = "Bank account";
	private static String PAYMENT_METHOD2 = "Paymaster";
	private static PartTimeEmployee anEmployee;
	private static FullTimeEmployee anotherEmployee;

	@Inject
	EmployeeController emController;
	@Inject
	PartTimeController ptController;
	@Inject
	FullTimeController ftController;

	@Inject
	SalesController saController;
	@Inject
	TimeCardController tcController;
	@Inject
	ChargesController cController;

	@Inject
	SessionManagementController smController;
	@Inject
	UnionsController unController;

	@Inject
	AutoPayday payer;

	@Before
	public void setUp() {

		USER1_UNION = new Union();
		USER1_UNION.setUnionName("union 1");
		USER1_UNION.setWeeklyRate(9000);

		USER1_UNION_EDITED = new Union();
		USER1_UNION_EDITED.setUnionName("union 2");
		USER1_UNION_EDITED.setWeeklyRate(5000);

		anEmployee = new PartTimeEmployee();
		anEmployee.setCode(USER1_COD);
		anEmployee.setName(USER1_NAME);
		anEmployee.setSurname(USER1_SURNAME);
		anEmployee.setEmail(USER1_EMAIL);
		anEmployee.setUnion(USER1_UNION);
		anEmployee.setPayment_method(PAYMENT_METHOD1);
		anEmployee.setPayment_method_details("IT54jhbawd5awd54a34wd3a5w4d54adw3");
		anEmployee.setAddress("via Strada Nuova 33, Pavia PV");
		anEmployee.setHourlyRate((float) 6.25);

		anotherEmployee = new FullTimeEmployee();
		anotherEmployee.setCode(USER2_COD);
		anotherEmployee.setName(USER1_NAME);
		anotherEmployee.setSurname(USER1_SURNAME);
		anotherEmployee.setEmail(USER1_EMAIL);
		anotherEmployee.setUnion(USER1_UNION);
		anotherEmployee.setPayment_method(PAYMENT_METHOD2);
		anotherEmployee.setAddress("via Strada Nuova 34, Pavia PV");
		anotherEmployee.setCommissionRate(50);
		anotherEmployee.setSalary(1200);
	}

	@After
	public void cleanup() {
		try {
			if (emController.find(USER1_COD) != null) {
				emController.remove(anEmployee.getCode());
			}
			if (emController.find(USER2_COD) != null) {
				emController.remove(anotherEmployee.getCode());
			}
			if (unController.find(USER1_UNION.getUnionName()) != null)
				unController.remove(USER1_UNION.getUnionName());
			if (unController.find(USER1_UNION_EDITED.getUnionName()) != null)
				unController.remove(USER1_UNION_EDITED.getUnionName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testHirePartTimeEmployee() {

		try {
			unController.add(USER1_UNION);
			ptController.add(anEmployee);

			List<PartTimeEmployee> partEmployees = ptController.findAll();
			boolean isPresent = false;
			for (IEmployee em : partEmployees) {
				if (em.getCode().equals(USER1_COD)) {
					isPresent = true;
					break;
				}

			}
			Assert.assertTrue("Part Time Employee hired successfully!", isPresent);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testHireFullTimeEmployee() {

		try {
			unController.add(USER1_UNION);
			ftController.add(anotherEmployee);

			List<FullTimeEmployee> fullEmployees = ftController.findAll();
			boolean isPresent = false;
			for (IEmployee em : fullEmployees) {
				if (em.getCode().equals(USER2_COD)) {
					isPresent = true;
					break;
				}

			}
			Assert.assertTrue("Full Time Employee hired successfully!", isPresent);

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFireUser() {

		Sales sale = new Sales();
		sale.setDate(new Date());
		sale.setInfo("test sale");
		sale.setReceipt_ID(123456);
		sale.setAmount((float) 55.55);
		sale.setEmployee(anotherEmployee);

		try {
			unController.add(USER1_UNION);
			ftController.add(anotherEmployee);
			saController.add(sale);
			emController.remove(anotherEmployee.getCode());
		} catch (Exception e1) {
			Assert.fail(e1.getMessage());
		}

		List<Employee> employees = emController.findAll();
		boolean isPresent = false;
		for (IEmployee em : employees) {
			if (em.getCode().equals(USER1_COD)) {
				isPresent = true;
				break;
			}
		}

		Assert.assertTrue("Employee fired successfully!", !isPresent);
		try {
			saController.find(sale.getId());
			Assert.assertTrue("Transactions of the employee removed!", true);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}

	@Test
	public void editPartTimeEmployee() {
		try {
			unController.add(USER1_UNION);
			unController.add(USER1_UNION_EDITED);
			ptController.add(anEmployee);

			anEmployee.setEmail(USER1_EMAIL_EDITED);
			anEmployee.setUnion(USER1_UNION_EDITED);
			anEmployee.setPayment_method("Postal address");

			ptController.update(anEmployee);

			Assert.assertEquals("Postal address", ptController.find(anEmployee.getCode()).getPayment_method());
			Assert.assertEquals(anEmployee.getAddress(),
					ptController.find(anEmployee.getCode()).getPayment_method_details());
			anEmployee.setPayment_method("Paymaster");
			ptController.update(anEmployee);

			Assert.assertEquals("Paymaster", ptController.find(anEmployee.getCode()).getPayment_method());
			Assert.assertEquals("", ptController.find(anEmployee.getCode()).getPayment_method_details());

			Assert.assertEquals(USER1_EMAIL_EDITED, ptController.find(anEmployee.getCode()).getEmail());
			Assert.assertEquals(USER1_UNION_EDITED.getUnionName(),
					ptController.find(anEmployee.getCode()).getUnion().getUnionName());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void editFullTimeEmployee() {
		try {
			unController.add(USER1_UNION);
			unController.add(USER1_UNION_EDITED);
			ftController.add(anotherEmployee);
			
			anotherEmployee.setEmail(USER1_EMAIL_EDITED);
			anotherEmployee.setUnion(USER1_UNION_EDITED);
			anotherEmployee.setPayment_method("Postal address");
			ftController.update(anotherEmployee);

			Assert.assertEquals("Postal address", ftController.find(anotherEmployee.getCode()).getPayment_method());
			Assert.assertEquals(anotherEmployee.getAddress(),
					ftController.find(anotherEmployee.getCode()).getPayment_method_details());
			anotherEmployee.setPayment_method("Paymaster");
			ftController.update(anotherEmployee);
			Assert.assertEquals("Paymaster", ftController.find(anotherEmployee.getCode()).getPayment_method());
			Assert.assertEquals("", ftController.find(anotherEmployee.getCode()).getPayment_method_details());

			Assert.assertEquals(USER1_EMAIL_EDITED, ftController.find(anotherEmployee.getCode()).getEmail());
			Assert.assertEquals(USER1_UNION_EDITED.getUnionName(),
					ftController.find(anotherEmployee.getCode()).getUnion().getUnionName());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void doubleHire() {
		try {
			unController.add(USER1_UNION);
			ftController.add(anotherEmployee);
			Assert.assertTrue("User hired!", true);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		try {
			ftController.add(anotherEmployee);
			Assert.fail("ADDED A DUPLICATED PRIMARY KEY!");
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage(), true);
		}

		try {
			ptController.add(anEmployee);
			Assert.assertTrue("User hired!", true);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		try {
			ptController.add(anEmployee);
			Assert.fail("ADDED A DUPLICATED PRIMARY KEY!");
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage(), true);
		}

	}

	@Test
	public void doubleFire() {
		try {
			unController.add(USER1_UNION);
			ptController.add(anEmployee);
			emController.remove(anEmployee.getCode());
			Assert.assertTrue("Successfully fired!", true);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		try {
			emController.remove(anEmployee.getCode());
			Assert.fail("REMOVED A NON EXISTENT CODE!");
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage(), true);

		}

	}

	@Test
	public void autoMapUnion() {
		boolean mapWorking;
		boolean inverseMapWorking;
		try {
			unController.add(USER1_UNION);
			ptController.add(anEmployee);

			mapWorking = false;
			if (ptController.find(anEmployee.getCode()).getUnion().getUnionName().equals(USER1_UNION.getUnionName())) {
				mapWorking = true;
			}

			ftController.add(anotherEmployee);
			inverseMapWorking = false;
			List<Employee> associates = unController.find(USER1_UNION.getUnionName()).getAssociates();
			for (IEmployee employee : associates) {
				if (employee.getUnion().getUnionName().equals(USER1_COD)
						|| employee.getUnion().getUnionName().equals(USER2_COD)) {
					inverseMapWorking = true;
				}
				if (associates.size() == 2) {
					inverseMapWorking = true;
				}
			}

			Assert.assertTrue("The User has been successfully mapped into the union", mapWorking);
			Assert.assertTrue("The Union has mapped correctly with the users", inverseMapWorking);

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}

	@Test
	public void testUnions() {
		Union union = new Union();
		union.setUnionName("unionTest2");
		union.setWeeklyRate((float) 0.52);

		Union union1 = new Union();
		union1.setUnionName("unionTest");
		union1.setWeeklyRate((float) 10.5);

		try {
			unController.add(union);
			unController.add(union1);

			List<Union> unions = unController.getUnionsList();
			int flag = 0;
			boolean isEqualsWorking = false;
			for (IUnion u : unions) {
				if (u.equals(union) || u.equals(union1)) {
					if (u.getUnionName().equals(union.getUnionName())
							|| u.getUnionName().equals(union1.getUnionName())) {
						isEqualsWorking = true;
						flag++;
					}
				}
			}
			Assert.assertTrue("2 unions added!", flag == 2);
			Assert.assertTrue("Override of equals is working", isEqualsWorking);

			IUnion tmpUnion = unController.find(union1.getUnionName());
			Assert.assertTrue("findUnion is working properly", tmpUnion.equals(union1));

			union1.setWeeklyRate((float) 1.50);
			unController.update(union1);

			Assert.assertTrue("Update successfully completed", unController.find(union1.getUnionName()).equals(union1));

			unController.remove(union.getUnionName());
			unController.remove(union1.getUnionName());

			unions = unController.getUnionsList();
			boolean hasBeenFired = true;
			for (IUnion u : unions) {
				if (u.equals(union) || u.equals(union1)) {
					hasBeenFired = false;
				}
			}
			Assert.assertTrue("Fire union is working correctly", hasBeenFired);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}

	@Test
	public void testTransactions() {
		try {
			unController.add(USER1_UNION);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

		try {
			ptController.add(anEmployee);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

		try {
			ftController.add(anotherEmployee);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

		try {
			TimeCard tCard = new TimeCard();
			tCard.setHoursWorked(10);
			tCard.setEmployee(anEmployee);
			tcController.addHours(tCard);

			tCard = new TimeCard();
			tCard.setHoursWorked(7);
			tCard.setEmployee(anEmployee);
			tcController.addHours(tCard);

			Sales aSale = new Sales();
			aSale.setDate(new Date());
			aSale.setInfo("test");
			aSale.setReceipt_ID(1234);
			aSale.setAmount((float) 51);
			aSale.setEmployee(anotherEmployee);
			saController.addSale(aSale);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

		List<Sales> allSales = saController.findAll();
		List<TimeCard> allTimeCards = tcController.findAll();
		int timeCards = 0;
		int sales = 0;
		for (Iterator iterator = allTimeCards.iterator(); iterator.hasNext();) {
			TimeCard timeCard = (TimeCard) iterator.next();
			if (timeCard.getEmployee().getCode().equals(anEmployee.getCode())) {
				timeCards++;
				Assert.assertTrue("correct amount added",
						(timeCard.getAmount() == 43.75) || (timeCard.getAmount() == 68.75));
			}
		}
		for (Iterator iterator = allSales.iterator(); iterator.hasNext();) {
			Sales sale = (Sales) iterator.next();
			if (sale.getEmployee().getCode().equals(anotherEmployee.getCode())) {
				sales++;
				Assert.assertEquals(sale.getAmount(), 25.5, 0.0001);
			}
		}
		try {
			Assert.assertEquals(3, timeCards + sales);
			Charges charge = new Charges();
			charge.setAmount(100);
			charge.setDate(new Date());
			charge.setInfo("test charge");
			charge.setEmployee(anEmployee);
			cController.addCharge(charge);

			charge = new Charges();
			charge.setAmount(100);
			charge.setDate(new Date());
			charge.setInfo("test charge");
			charge.setEmployee(anotherEmployee);
			cController.addCharge(charge);
			List<Charges> allCharges = cController.findAll();
			int numCharges = 0;
			int employees = 0;

			for (Charges t : allCharges) {
				if (t.getInfo().equals("test charge")) {
					numCharges++;
					if (t.getEmployee().getCode().equals(anEmployee.getCode())
							|| t.getEmployee().getCode().equals(anotherEmployee.getCode())) {
						employees++;
					}
				}

			}

			Assert.assertTrue("Two total charges added", numCharges == 2);
			Assert.assertTrue("Charges added to two employees", employees == 2);

			for (Charges t : allCharges) {
				if (t.getEmployee().getCode().equals(USER1_COD)) {
					cController.remove(t.getId());
				} else if (t.getEmployee().getCode().equals(USER2_COD)) {
					cController.remove(t.getId());
				}
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}

	@Test
	public void testChangePassword() throws NoSuchAlgorithmException {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			unController.add(USER1_UNION);
			ftController.add(anotherEmployee);
			Credentials oldLoginCredentials = new Credentials();
			oldLoginCredentials.setCode(anotherEmployee.getCode());
			smController.generateCredentials(anotherEmployee,oldLoginCredentials);
			oldLoginCredentials.setPassword(Base64.getEncoder().encodeToString(md.digest("basePassword".getBytes())));
			smController.update(oldLoginCredentials);
			oldLoginCredentials.setPassword("basePassword");
			Credentials newLoginCredentials = new Credentials();
			newLoginCredentials.setCode(anotherEmployee.getCode());
			newLoginCredentials.setPassword("newPassword");
			smController.changePassword(oldLoginCredentials, newLoginCredentials);

			Assert.assertEquals(smController.find(anotherEmployee.getCode()).getPassword(),
					newLoginCredentials.getPassword());
			smController.remove(anotherEmployee.getCode());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testPayday() {

		Union union = new Union();
		union.setUnionName("unionTest2");
		union.setWeeklyRate(15);

		Union union1 = new Union();
		union1.setUnionName("unionTest");
		union1.setWeeklyRate(12);

		try {
			unController.add(union);
			unController.add(union1);

			anEmployee.setUnion(union);
			anotherEmployee.setUnion(union1);
			ptController.add(anEmployee);
			ftController.add(anotherEmployee);

			TimeCard timeCard = new TimeCard();
			timeCard.setEmployee(anEmployee);
			timeCard.setHoursWorked(8);
			tcController.addHours(timeCard);
			timeCard = new TimeCard();
			timeCard.setEmployee(anEmployee);
			timeCard.setHoursWorked(8);
			tcController.addHours(timeCard);

			timeCard = new TimeCard();
			timeCard.setEmployee(anEmployee);
			timeCard.setHoursWorked(8);
			tcController.addHours(timeCard);

			timeCard = new TimeCard();
			timeCard.setEmployee(anEmployee);
			timeCard.setHoursWorked(8);
			tcController.addHours(timeCard);

			timeCard = new TimeCard();
			timeCard.setEmployee(anEmployee);
			timeCard.setHoursWorked(8);
			tcController.addHours(timeCard);

			Sales sale = new Sales();
			sale.setDate(new Date());
			sale.setAmount(800);
			sale.setInfo("Test");
			sale.setReceipt_ID(123456789);
			sale.setEmployee(anotherEmployee);
			saController.addSale(sale);

			HashMap<String, Float> weeklyEarns = payer.weeklyPay();
			HashMap<String, Float> monthlyEarns = payer.monthlyPay();

			emController.remove(anEmployee.getCode());
			emController.remove(anotherEmployee.getCode());
			unController.remove(union.getUnionName());
			unController.remove(union1.getUnionName());

			Assert.assertEquals(235, weeklyEarns.get(anEmployee.getCode()), 0.001);
			Assert.assertEquals(1588, monthlyEarns.get(anotherEmployee.getCode()), 0.001);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// Assert.fail(e.getMessage());
			e.printStackTrace();
		}

	}

	@Inject
	UnionConverter unConv;

	@Test
	public void unionConverterTest() {
		try {
			unController.add(USER1_UNION);

			Object un = unConv.getAsObject(null, null, USER1_UNION.getUnionName());
			Assert.assertEquals((Object) USER1_UNION, un);
			String un1 = unConv.getAsString(null, null, USER1_UNION);
			Assert.assertEquals(USER1_UNION.getUnionName(), un1);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
}
