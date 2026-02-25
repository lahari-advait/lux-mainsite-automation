package data;

public class TreatmentData {

    private final String department;
    private final String subDepartment;
    private final String url;

    public TreatmentData(String department, String subDepartment, String url) {
        this.department = department;
        this.subDepartment = subDepartment;
        this.url = url;
    }

    public String getDepartment() {
        return department;
    }

    public String getSubDepartment() {
        return subDepartment;
    }

    public String getUrl() {
        return url;
    }
}

