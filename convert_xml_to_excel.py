import xml.etree.ElementTree as ET
import pandas as pd
import glob

def parse_xml(xml_file):
    tree = ET.parse(xml_file)
    root = tree.getroot()
    test_cases = []

    for testcase in root.findall(".//testcase"):
        name = testcase.get("name")
        classname = testcase.get("classname")
        time = testcase.get("time")
        status = "Passed"
        failure = testcase.find("failure")
        if failure is not None:
            status = "Failed"
        test_cases.append([name, classname, time, status])

    return test_cases

def main():
    xml_files = glob.glob("target/surefire-reports/TEST-*.xml")
    all_test_cases = []
    for xml_file in xml_files:
        all_test_cases.extend(parse_xml(xml_file))
    df = pd.DataFrame(all_test_cases, columns=["Test Name", "Class Name", "Time", "Status"])
    df.to_excel("target/surefire-reports/test-report.xlsx", index=False)

if __name__ == "__main__":
    main()
