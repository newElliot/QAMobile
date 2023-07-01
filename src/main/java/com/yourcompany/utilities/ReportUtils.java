package com.yourcompany.utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IInvokedMethod;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.collections.Lists;

public class ReportUtils {
	public static Logger logger = LogManager.getLogger(ReportUtils.class);
	private PrintWriter out;
	private static StringBuffer summaryHTML = new StringBuffer();
	private static StringBuffer detailSummaryHTML = new StringBuffer();
	private static StringBuffer consolidatedSummaryHTML = new StringBuffer();
	private static HashMap<String, String> testMethodList = new HashMap<>();
	private static ArrayList<String[]> rowCollection = new ArrayList<>();
	private static int numOfPass = 0;
	private static int numOfFail = 0;
	private static int numOfSkip = 0;
	private static String startTime, endTime, totalTime;

	private static int numOfPassAll = 0;
	private static int numOfFailAll = 0;
	private static int numOfSkipAll = 0;
	private static long startTimeAll = Long.MAX_VALUE;
	private static long endTimeAll;
	private static long startTimeDate = 0;
	private static long endTimeDate = 0;
	private static int numberOfSuite = 0;

	public void generateReport(List<ISuite> suites) throws Exception {
		try {
			out = createWriter("test-report");
		} catch (IOException e) {
			throw new IOException("        !!!Error on create test-report folder");
		}

		startHtml(out);
		summaryHTML.append("<h2>Summary of each Test run </h2>");
		summaryHTML.append(
				"<table cellspacing=\"0\" cellpadding=\"0\" class = \"testOverview\" style=\"padding-bottom:2em\" width = '1000'>");
		summaryHTML.append("<tr bgcolor = '#ccddff'>");
		summaryHTML.append("<th>Test Name</th>");
		summaryHTML.append("<th># Total</th>");
		summaryHTML.append("<th># Passed</th>");
		summaryHTML.append("<th># Skipped</th>");
		summaryHTML.append("<th># Failed</th>");
		summaryHTML.append("<th>Start Time</th>");
		summaryHTML.append("<th>End Time</th>");
		summaryHTML.append("<th>Total Time(hh:mm:ss)</th>");
		summaryHTML.append("</tr>");

		detailSummaryHTML.append("<br><h2>Detail of Test run methods </h2><br>");
		detailSummaryHTML.append(
				"<table cellspacing=\"0\" cellpadding=\"0\" class=\"methodOverview\" id = \"summary\" style=\"padding-bottom:2em\" width = '1000'>");
		detailSummaryHTML.append(
				"<tr bgcolor='#ccddff'><th>Class</th><th>Method</th><th>Status </th><th>Run Time<br/>(hh:mm:ss)</th></tr>");
		generateMethodSummaryReport(suites);
		summaryHTML.append("</table>");
		detailSummaryHTML.append("</table>");

		if (numberOfSuite > 1) {

			consolidatedSummaryHTML.append("<table cellspacing=\"0\" cellpadding=\"0\" class = \"testOverview\" style=\"padding-bottom:2em\" width = '1000'>");
			consolidatedSummaryHTML.append("<h2>Consolidated Summary</h2>");
			consolidatedSummaryHTML.append("<tr bgcolor = '#ccddff'>");
			consolidatedSummaryHTML.append("<th># Total</th>");
			consolidatedSummaryHTML.append("<th># Passed</th>");
			consolidatedSummaryHTML.append("<th># Skipped</th>");
			consolidatedSummaryHTML.append("<th># Failed</th>");
			consolidatedSummaryHTML.append("<th>Start Time</th>");
			consolidatedSummaryHTML.append("<th>End Time</th>");
			consolidatedSummaryHTML.append("<th>Total Time(hh:mm:ss)</th>");
			consolidatedSummaryHTML.append("</tr>");

			consolidatedSummaryHTML.append("<tr>");
			consolidatedSummaryHTML.append("<td>");
			consolidatedSummaryHTML.append(numOfPassAll + numOfFailAll + numOfSkipAll);
			consolidatedSummaryHTML.append("</td>");
			consolidatedSummaryHTML.append("<td>");
			consolidatedSummaryHTML.append(numOfPassAll);
			consolidatedSummaryHTML.append("</td>");
			consolidatedSummaryHTML.append("<td>");
			consolidatedSummaryHTML.append(numOfSkipAll);
			consolidatedSummaryHTML.append("</td>");
			consolidatedSummaryHTML.append("<td>");
			consolidatedSummaryHTML.append(numOfFailAll);
			consolidatedSummaryHTML.append("</td>");
			consolidatedSummaryHTML.append("<td>");
			consolidatedSummaryHTML.append(new SimpleDateFormat("kk:mm:ss").format(new Date(startTimeAll)));
			consolidatedSummaryHTML.append("</td>");
			consolidatedSummaryHTML.append("<td>");
			consolidatedSummaryHTML.append(new SimpleDateFormat("kk:mm:ss").format(new Date(endTimeAll)));
			consolidatedSummaryHTML.append("</td>");
			consolidatedSummaryHTML.append("<td>");
			consolidatedSummaryHTML.append(millisToTimeConversion((endTimeAll - startTimeAll) / 1000));
			consolidatedSummaryHTML.append("</td>");
			consolidatedSummaryHTML.append("</tr>");
			consolidatedSummaryHTML.append("</table>");
			out.print(consolidatedSummaryHTML);

		}

		out.print(summaryHTML);
		out.print(detailSummaryHTML);
		endHtml(out);
		out.flush();
		out.close();
	}

	private PrintWriter createWriter(String outdir) throws IOException {
		new File(outdir).mkdirs();
		logger.info("Created output directory " + outdir);
		return new PrintWriter(
				new BufferedWriter(new FileWriter(new File(outdir, "customized-emailable-report.html"))));
	}

	private void startHtml(PrintWriter out) {
		out.println(
				"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
		out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
		out.println("<head>");
		out.println("<title>Automation Report</title>");
		out.println("<style type=\"text/css\">");
		out.println("table {margin-bottom:10px;border-collapse:collapse;empty-cells:show}");
		out.println("td,th {border:1px solid #009;padding:.25em .5em}");
		out.println(".result th {vertical-align:bottom}");
		out.println(".param th {padding-left:1em;padding-right:1em}");
		out.println(".param td {padding-left:.5em;padding-right:2em}");
		out.println(".stripe td,.stripe th {background-color: #E6EBF9}");
		out.println(".numi,.numi_attn {text-align:right}");
		out.println(".total td {font-weight:bold}");
		out.println(".passedodd td {background-color: #0A0}");
		out.println(".passedeven td {background-color: #3F3}");
		out.println(".skippedodd td {background-color: #CCC}");
		out.println(".skippedodd td {background-color: #DDD}");
		out.println(".failedodd td,.numi_attn {background-color: #F33}");
		out.println(".failedeven td,.stripe .numi_attn {background-color: #D00}");
		out.println(".stacktrace {white-space:pre;font-family:monospace}");
		out.println(".totop {font-size:85%;text-align:center;border-bottom:2px solid #000}");
		out.println("</style>");
		out.println("</head>");
		out.println("<body>");
		out.println("Date Run: " + new SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime()));
	}

	/**
	 * Creates a table showing the highlights of each test method with links to the
	 * method details
	 */
	private void generateMethodSummaryReport(List<ISuite> suites) {
		for (ISuite suite : suites) {
			Map<String, ISuiteResult> r = suite.getResults();
			for (ISuiteResult r2 : r.values()) {
				ITestContext testContext = r2.getTestContext();
				resultSummary(suite, testContext);
			}
		}
	}

	private String millisToTimeConversion(long seconds) {

		final int MINUTES_IN_AN_HOUR = 60;
		final int SECONDS_IN_A_MINUTE = 60;

		int minutes = (int) (seconds / SECONDS_IN_A_MINUTE);
		seconds -= minutes * SECONDS_IN_A_MINUTE;

		int hours = minutes / MINUTES_IN_AN_HOUR;
		minutes -= hours * MINUTES_IN_AN_HOUR;

		return prefixZeroToDigit(hours) + ":" + prefixZeroToDigit(minutes) + ":" + prefixZeroToDigit((int) seconds);
	}

	private String prefixZeroToDigit(int num) {
		int number = num;
		if (number <= 9) {
			String sNumber = "0" + number;
			return sNumber;
		} else
			return "" + number;

	}

	/** Finishes HTML stream */
	private void endHtml(PrintWriter out) {
		out.println("</body></html>");
	}

	private void resultSummary(ISuite suite, ITestContext testContext) {
		String[] rowresult = null;
		rowCollection = new ArrayList<>();
		testMethodList = new HashMap<>();
		IResultMap resultMap = null;
		String[] testSuiteSummary = new String[7];
		String testName = ""; // name of Test in xml
		int numOfTCinSuite = 0;
		boolean hasAddedSpan = false;
		numOfPass = 0;
		numOfFail = 0;
		numOfSkip = 0;
		startTimeDate = testContext.getStartDate().getTime();
		endTimeDate = testContext.getEndDate().getTime();
		startTime = new SimpleDateFormat("kk:mm:ss").format(testContext.getStartDate());
		endTime = new SimpleDateFormat("kk:mm:ss").format(testContext.getEndDate());
		totalTime = millisToTimeConversion(
				(testContext.getEndDate().getTime() - testContext.getStartDate().getTime()) / 1000);

		resultMap = testContext.getPassedTests();
		for (ITestNGMethod method : getMethodSet(resultMap, suite)) {
			numOfPass++;
			numOfTCinSuite++;
			rowresult = new String[4];
			rowresult[0] = method.getTestClass().getName();
			rowresult[1] = qualifiedName(method);
			rowresult[2] = getStatus(resultMap.getResults(method).toArray(new ITestResult[] {})[0]);
			rowresult[3] = getTotalTimeTakenForTestCase(method, resultMap);
			rowCollection.add(rowresult);
			testName = method.getXmlTest().getName();
			testMethodList.put(rowresult[1], rowresult[2]);
		}
		resultMap = testContext.getFailedTests();
		for (ITestNGMethod method : getMethodSet(resultMap, suite)) {
			numOfFail++;
			numOfTCinSuite++;
			rowresult = new String[4];
			rowresult[0] = method.getTestClass().getName();
			rowresult[1] = qualifiedName(method);
			rowresult[2] = getStatus(resultMap.getResults(method).toArray(new ITestResult[] {})[0]);
			rowresult[3] = getTotalTimeTakenForTestCase(method, resultMap);
			rowCollection.add(rowresult);
			testName = method.getXmlTest().getName();
			testMethodList.put(rowresult[1], rowresult[2]);
		}
		resultMap = testContext.getSkippedTests();
		for (ITestNGMethod method : getMethodSet(resultMap, suite)) {
			if (!testMethodList.containsKey(qualifiedName(method))) {
				numOfSkip++;
				numOfTCinSuite++;
				rowresult = new String[4];
				rowresult[0] = method.getTestClass().getName();
				rowresult[1] = qualifiedName(method);
				rowresult[2] = getStatus(resultMap.getResults(method).toArray(new ITestResult[] {})[0]);
				rowresult[3] = getTotalTimeTakenForTestCase(method, resultMap);
				rowCollection.add(rowresult);
				testName = method.getXmlTest().getName();
				testMethodList.put(rowresult[1], rowresult[2]);
			}
		}
		testSuiteSummary[0] = numOfPass + "";
		testSuiteSummary[1] = numOfSkip + "";
		testSuiteSummary[2] = numOfFail + "";
		testSuiteSummary[3] = startTime;
		testSuiteSummary[4] = endTime;
		testSuiteSummary[5] = totalTime;
		testSuiteSummary[6] = testName;
		generateSuiteSummaryReport(testSuiteSummary);

		for (String[] row : rowCollection) {
			detailSummaryHTML.append("<tr>");
			if (numOfTCinSuite > 1 && !hasAddedSpan) {
				detailSummaryHTML.append("<td rowspan=\"" + numOfTCinSuite + "\">");
				detailSummaryHTML.append(row[0]);
				detailSummaryHTML.append("</td>");
				hasAddedSpan = true;
			} else {
				detailSummaryHTML.append("<td>");
				detailSummaryHTML.append(row[0]);
				detailSummaryHTML.append("</td>");
			}
			detailSummaryHTML.append("<td>");
			detailSummaryHTML.append(row[1]);
			detailSummaryHTML.append("</td>");
			detailSummaryHTML.append("<td style=\"text-align:right\">");
			detailSummaryHTML.append(row[2]);
			detailSummaryHTML.append("</td>");
			detailSummaryHTML.append("<td class=\"numi\">");
			detailSummaryHTML.append(row[3]);
			detailSummaryHTML.append("</td>");
			detailSummaryHTML.append("</tr>");
		}

		if (numberOfSuite > 0) {
			numOfFailAll = numOfFailAll + numOfFail;
			numOfPassAll = numOfPassAll + numOfPass;
			numOfSkipAll = numOfSkipAll + numOfSkip;
			startTimeAll = Math.min(startTimeDate, startTimeAll);
			endTimeAll = Math.max(endTimeDate, endTimeAll);
		}

	}

	private Collection<ITestNGMethod> getMethodSet(IResultMap tests, ISuite suite) {
		List<IInvokedMethod> r = Lists.newArrayList();
		List<IInvokedMethod> invokedMethods = suite.getAllInvokedMethods();
		for (IInvokedMethod im : invokedMethods) {
			if (tests.getAllMethods().contains(im.getTestMethod())) {
				r.add(im);
			}
		}
		Collections.sort(r, new TestSorter());
		List<ITestNGMethod> result = Lists.newArrayList();

		for (IInvokedMethod m : r) {
			for (ITestNGMethod temp : result) {
				if (!temp.equals(m.getTestMethod()))
					result.add(m.getTestMethod());
			}
		}

		Collection<ITestNGMethod> allMethodsCollection = tests.getAllMethods();
		List<ITestNGMethod> allMethods = new ArrayList<ITestNGMethod>(allMethodsCollection);
		Collections.sort(allMethods, new TestMethodSorter());
		
		for (ITestNGMethod m : allMethods) {
			if (!result.contains(m)) {
				result.add(m);
			}
		}
		return result;
	}

	private String qualifiedName(ITestNGMethod method) {
		StringBuilder addon = new StringBuilder();
		String[] groups = method.getGroups();
		int length = groups.length;
		if (length > 0 && !"basic".equalsIgnoreCase(groups[0])) {
			addon.append("(");
			for (int i = 0; i < length; i++) {
				if (i > 0) {
					addon.append(", ");
				}
				addon.append(groups[i]);
			}
			addon.append(")");
		}

		return "<b>" + method.getMethodName() + "</b> " + addon;
	}

	private String getStatus(ITestResult result) {
		String resultText = null;
		if (result.getStatus() == ITestResult.SUCCESS) {
			resultText = "<font color= 'green' ><b>PASS</b></font>";
		} else if (result.getStatus() == ITestResult.FAILURE) {
			resultText = "<font color= 'red' ><b>FAIL</b></font>";
		} else if (result.getStatus() == ITestResult.SKIP) {
			resultText = "<font color= '#606060' ><b>SKIP</b></font>";
		}

		return resultText;
	}

	private String getTotalTimeTakenForTestCase(ITestNGMethod method, IResultMap resultMap) {
		long end = Long.MIN_VALUE;
		long start = Long.MAX_VALUE;
		long startMS = 0;
		for (ITestResult testResult : resultMap.getResults(method)) {
			if (testResult.getEndMillis() > end) {
				end = testResult.getEndMillis() / 1000;
			}
			if (testResult.getStartMillis() < start) {
				startMS = testResult.getStartMillis();
				start = startMS / 1000;
			}
		}

		return millisToTimeConversion(end - start);
	}

	private void generateSuiteSummaryReport(String[] suiteDetail) {
		numberOfSuite++;
		summaryHTML.append("<tr>");
		summaryHTML.append("<td>");
		summaryHTML.append(suiteDetail[6]);
		summaryHTML.append("</td>");
		summaryHTML.append("<td>");
		summaryHTML.append(Integer.parseInt(suiteDetail[0]) + Integer.parseInt(suiteDetail[1]) + Integer.parseInt(suiteDetail[2]));
		summaryHTML.append("</td>");
		summaryHTML.append("<td>");
		summaryHTML.append(suiteDetail[0]);
		summaryHTML.append("</td>");
		summaryHTML.append("<td>");
		summaryHTML.append(suiteDetail[1]);
		summaryHTML.append("</td>");
		summaryHTML.append("<td>");
		summaryHTML.append(suiteDetail[2]);
		summaryHTML.append("</td>");
		summaryHTML.append("<td>");
		summaryHTML.append(suiteDetail[3]);
		summaryHTML.append("</td>");
		summaryHTML.append("<td>");
		summaryHTML.append(suiteDetail[4]);
		summaryHTML.append("</td>");
		summaryHTML.append("<td>");
		summaryHTML.append(suiteDetail[5]);
		summaryHTML.append("</td>");
		summaryHTML.append("</tr>");
	}

	/** Arranges methods by classname and method name */
	private class TestSorter implements Comparator<IInvokedMethod> {
		@Override
		public int compare(IInvokedMethod o1, IInvokedMethod o2) {
			int r = o1.getTestMethod().getTestClass().getName().compareTo(o2.getTestMethod().getTestClass().getName());
			if (r == 0) {
				r = o1.getTestMethod().getMethodName().compareTo(o2.getTestMethod().getMethodName());
			}
			return r;
		}

	}

	private class TestMethodSorter implements Comparator<ITestNGMethod> {
		@Override
		public int compare(ITestNGMethod o1, ITestNGMethod o2) {
			int r = o1.getTestClass().getName().compareTo(o2.getTestClass().getName());
			if (r == 0) {
				r = o1.getMethodName().compareTo(o2.getMethodName());
			}
			return r;
		}
	}
}
