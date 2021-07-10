package com.bits.jobhunt;

public class Model {

    String CompanyName,Description,JobTitle,JobType,CompanyLocation,Qualification,Salary,Vacancy,Uid;

    public Model() {
    }

    public Model(String companyName, String description, String jobTitle, String jobType, String Companylocation, String qualification , String salary, String Vacancy, String uid) {
        CompanyName = companyName;
        Description = description;
        JobTitle = jobTitle ;
        JobType = jobType;
        CompanyLocation = Companylocation;
        Qualification = qualification;
        Salary = salary;
        this.Vacancy = Vacancy;
        Uid = uid;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public Model(String companyName, String JobTitle, String CompanyLocation, String salary) {
        CompanyName = companyName;
        JobTitle = JobTitle;
        CompanyLocation = CompanyLocation;
        Salary = salary;
    }

    public Model(String companyName, String description, String JobTitle, String jobType, String CompanyLocation, String qualification, String salary, String vacancy) {
        CompanyName = companyName;
        Description = description;
        JobTitle = JobTitle;
        JobType = jobType;
        CompanyLocation = CompanyLocation;
        Qualification = qualification;
        Salary = salary;
        this.Vacancy = vacancy;
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

    public String getJobTitle() {
        return JobTitle;
    }

    public void setJobTitle(String jobName) {
        JobTitle = jobName;
    }

    public String getJobType() {
        return JobType;
    }

    public void setJobType(String jobType) {
        JobType = jobType;
    }

    public String getCompanyLocation() {
        return CompanyLocation;
    }

    public void setCompanyLocation(String CompanyLocation) {
        CompanyLocation = CompanyLocation;
    }

    public String getQualification() {
        return Qualification;
    }

    public void setQualification(String qualification) {
        Qualification = qualification;
    }

    public String getSalary() {
        return Salary;
    }

    public void setSalary(String salary) {
        Salary = salary;
    }

    public String getVacancy() {
        return Vacancy;
    }

    public void setVacancy(String vacancy) {
        this.Vacancy = Vacancy;
    }
}
