package com.bits.jobhunt;

public class Model {

    String CompanyName,Description,JobName,JobType,Location,Qualifications,Salary,numberOFHires,Uid,name,url,CompanyLocation,JobTitle,Vacancy,Qualification,imageurl;

    public Model() {
    }

//    public Model(String companyName, String description, String jobTitle, String jobType, String companyLocation, String qualification, String salary, String vacancy){
//        CompanyName = companyName;
//        Description = description;
//        JobTitle = jobTitle;
//        JobType = jobType;
//        CompanyLocation = companyLocation;
//        Qualification = qualification;
//        Salary = salary;
//        Vacancy = vacancy;
//    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public Model(String imageurl) {
        this.imageurl = imageurl;
    }



    public Model(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Model(String companyName, String description, String jobName, String jobType, String location, String qualifications, String salary, String numberOFHires, String uid) {
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

    public String getVacancy() {
        return Vacancy;
    }

    public void setVacancy(String vacancy) {
        Vacancy = vacancy;
    }

    public String getQualification() {
        return Qualification;
    }

    public void setQualification(String qualification) {
        Qualification = qualification;
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
