package com.bits.jobhunt;

public class Model {

    String documentId, CompanyName,Description,JobName,JobType,Location,Qualifications,Salary,numberOFHires,Uid,CompanyLocation,JobTitle,Qualification,Vacancy;

    public Model() {
    }

    public Model(String companyName,String description, String jobName, String jobType, String location, String qualifications, String salary, String numberOFHires, String uid) {
        CompanyName = companyName;
        Description = description;
        JobName = jobName;
        JobType = jobType;
        Location = location;
        Qualifications = qualifications;
        Salary = salary;
        this.numberOFHires = numberOFHires;
        Uid = uid;
    }

    public String getDocumentId(){
        return documentId;
    }
    public void setDocumentId(String documentId){
        this.documentId = documentId;
    }
    public String getCompanyLocation() {
        return CompanyLocation;
    }

    public void setCompanyLocation(String companyLocation) {
        CompanyLocation = companyLocation;
    }

    public String getJobTitle() {
        return JobTitle;
    }

    public void setJobTitle(String jobTitle) {
        JobTitle = jobTitle;
    }

    public String getQualification() {
        return Qualification;
    }

    public void setQualification(String qualification) {
        Qualification = qualification;
    }

    public String getVacancy() {
        return Vacancy;
    }

    public void setVacancy(String vacancy) {
        Vacancy = vacancy;
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

    public Model(String companyName, String description, String jobName, String jobType, String location, String qualifications, String salary, String numberOFHires) {
        CompanyName = companyName;
        Description = description;
        JobName = jobName;
        JobType = jobType;
        Location = location;
        Qualifications = qualifications;
        Salary = salary;
        this.numberOFHires = numberOFHires;
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
}
