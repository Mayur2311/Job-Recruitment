package com.bits.jobhunt;

public class Model {

    String CompanyName,Description,JobName,JobType,Location,Qualifications,Salary,numberOFHires, JobCategory, Uid;

    public Model() {
    }

    public Model(String companyName, String description, String jobName, String jobType, String location, String qualifications, String salary, String numberOFHires, String jobCategory, String uid) {
        CompanyName = companyName;
        Description = description;
        JobName = jobName;
        JobType = jobType;
        Location = location;
        Qualifications = qualifications;
        Salary = salary;
        this.numberOFHires = numberOFHires;
        JobCategory = jobCategory;
        Uid = uid;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public Model(String companyName, String jobName, String location, String salary) {
        CompanyName = companyName;
        JobName = jobName;
        Location = location;
        Salary = salary;
    }

    public Model(String companyName, String description, String jobName, String jobType, String location, String qualifications, String salary, String numberOFHires, String jobCategory) {
        CompanyName = companyName;
        Description = description;
        JobName = jobName;
        JobType = jobType;
        Location = location;
        Qualifications = qualifications;
        Salary = salary;
        this.numberOFHires = numberOFHires;
        JobCategory = jobCategory;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getJobName() {
        return JobName;
    }

    public void setJobName(String jobName) {
        JobName = jobName;
    }

    public String getJobType() {
        return JobType;
    }

    public void setJobType(String jobType) {
        JobType = jobType;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getQualifications() {
        return Qualifications;
    }

    public void setQualifications(String qualifications) {
        Qualifications = qualifications;
    }

    public String getSalary() {
        return Salary;
    }

    public void setSalary(String salary) {
        Salary = salary;
    }

    public String getNumberOFHires() {
        return numberOFHires;
    }

    public void setNumberOFHires(String numberOFHires) {
        this.numberOFHires = numberOFHires;
    }

    public String getJobCategory() { return JobCategory; }

    public void setJobCategory(String jobCategory) { JobCategory = jobCategory; }
}
