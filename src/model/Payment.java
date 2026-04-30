package model;

public class Payment {

    // Fields (attributes of a payment)
    private String paymentId;
    private String patientId;
    private double amount;
    private String date;
    private String status; // "Pending", "Completed", "Failed"
    private String method; // "Cash", "Card", "Online"

    // Constructor — used to create a new Payment object
    public Payment(String paymentId, String patientId, double amount, String date, String status, String method) {
        this.paymentId = paymentId;
        this.patientId = patientId;
        this.amount    = amount;
        this.date      = date;
        this.status    = status;
        this.method    = method;
    }

    // Getters — used to read the values
    public String getPaymentId() { return paymentId; }
    public String getPatientId() { return patientId; }
    public double getAmount()    { return amount; }
    public String getDate()      { return date; }
    public String getStatus()    { return status; }
    public String getMethod()    { return method; }

    // Setters — used to update the values
    public void setStatus(String status) { this.status = status; }
    public void setMethod(String method) { this.method = method; }
    public void setAmount(double amount) { this.amount = amount; }

    // Converts a Payment object into a line of text for saving to payments.txt
    public String toFileString() {
        return paymentId + "," + patientId + "," + amount + "," + date + "," + status + "," + method;
    }

    // Converts a line of text from payments.txt back into a Payment object
    public static Payment fromFileString(String line) {
        String[] parts = line.split(",");
        return new Payment(
                parts[0],
                parts[1],
                Double.parseDouble(parts[2]),
                parts[3],
                parts[4],
                parts[5]
        );
    }

    // Returns a readable summary of the payment (useful for displaying in UI)
    @Override
    public String toString() {
        return "Payment[" + paymentId + "] Patient: " + patientId +
                " | Amount: " + amount + " | Date: " + date +
                " | Status: " + status + " | Method: " + method;
    }
}
