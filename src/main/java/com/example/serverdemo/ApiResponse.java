package com.example.serverdemo;


import java.util.List;

/**
 * See https://confluence.cares.cardinalhealth.net:8443/display/AI/Get+Fee+Schedule+data.
 */
public class ApiResponse {

    private String planName;                    // Plan name can change during processing – for example, BLUECARD program.
    private String taxId;                       // Tax ID.
    private String feeScheduleEffectiveDate;    // Effective date of the fee schedule.
    private String feeScheduleTerminationDate;  // Termination date of the fee schedule.
    private String message;                     // Message.
    private List<ServiceLine> lines;            // Array of ServiceLine objects.
    private String error;                       // Description of error.

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getFeeScheduleEffectiveDate() {
        return feeScheduleEffectiveDate;
    }

    public void setFeeScheduleEffectiveDate(String feeScheduleEffectiveDate) {
        this.feeScheduleEffectiveDate = feeScheduleEffectiveDate;
    }

    public String getFeeScheduleTerminationDate() {
        return feeScheduleTerminationDate;
    }

    public void setFeeScheduleTerminationDate(String feeScheduleTerminationDate) {
        this.feeScheduleTerminationDate = feeScheduleTerminationDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ServiceLine> getLines() {
        return lines;
    }

    public void setLines(List<ServiceLine> lines) {
        this.lines = lines;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public static class ServiceLine {
        Number feeSchedulePayerId;      // NHXS unique identifier.
        String feeScheduleNumber;       // NHXS fee schedule number.
        String effectiveDate;           // Fee schedule effective date.
        String terminationDate;         // Fee schedule termination date.
        Number lineNumber;              // Customer specific line number. (?)
        String procedureCode;           // Customer specific procedure code.
        String vendorCode;              // Customer specific vendor code.
        String itemNumber;              // Customer specific item number.
        String modifierCode;            // Modifier code.
        String procedureGroup;          // Customer specific procedure group.
        Number retailAmount;            // Retail amount.
        Number cost;                    // Cost.
        Number submittedUnits;          // Originally submitted units.
        Number extendedUnits;           // Extended units.
        String alternateProcedureCode;  // Alternate procedure code based on fee schedule.
        Number chargeAmount;            // Retail amount or alternate built charge.
        Number feeScheduleAmount;       // Rate from fee schedule.
        Number defaultNonCoveredRate;   // Default non-covered rate.
        Boolean exclusion;              // Indicator if service is excluded (true/false).
        String exclusionReason;         // Reason for exclusion.
        Number units;                   // Submitted or extended units.
        Boolean isCapitation;

        public Number getFeeSchedulePayerId() {
            return feeSchedulePayerId;
        }

        public void setFeeSchedulePayerId(Number feeSchedulePayerId) {
            this.feeSchedulePayerId = feeSchedulePayerId;
        }

        public String getFeeScheduleNumber() {
            return feeScheduleNumber;
        }

        public void setFeeScheduleNumber(String feeScheduleNumber) {
            this.feeScheduleNumber = feeScheduleNumber;
        }

        public String getEffectiveDate() {
            return effectiveDate;
        }

        public void setEffectiveDate(String effectiveDate) {
            this.effectiveDate = effectiveDate;
        }

        public String getTerminationDate() {
            return terminationDate;
        }

        public void setTerminationDate(String terminationDate) {
            this.terminationDate = terminationDate;
        }

        public Number getLineNumber() {
            return lineNumber;
        }

        public void setLineNumber(Number lineNumber) {
            this.lineNumber = lineNumber;
        }

        public String getProcedureCode() {
            return procedureCode;
        }

        public void setProcedureCode(String procedureCode) {
            this.procedureCode = procedureCode;
        }

        public String getVendorCode() {
            return vendorCode;
        }

        public void setVendorCode(String vendorCode) {
            this.vendorCode = vendorCode;
        }

        public String getItemNumber() {
            return itemNumber;
        }

        public void setItemNumber(String itemNumber) {
            this.itemNumber = itemNumber;
        }

        public String getModifierCode() {
            return modifierCode;
        }

        public void setModifierCode(String modifierCode) {
            this.modifierCode = modifierCode;
        }

        public String getProcedureGroup() {
            return procedureGroup;
        }

        public void setProcedureGroup(String procedureGroup) {
            this.procedureGroup = procedureGroup;
        }

        public Number getRetailAmount() {
            return retailAmount;
        }

        public void setRetailAmount(Number retailAmount) {
            this.retailAmount = retailAmount;
        }

        public Number getCost() {
            return cost;
        }

        public void setCost(Number cost) {
            this.cost = cost;
        }

        public Number getSubmittedUnits() {
            return submittedUnits;
        }

        public void setSubmittedUnits(Number submittedUnits) {
            this.submittedUnits = submittedUnits;
        }

        public Number getExtendedUnits() {
            return extendedUnits;
        }

        public void setExtendedUnits(Number extendedUnits) {
            this.extendedUnits = extendedUnits;
        }

        public String getAlternateProcedureCode() {
            return alternateProcedureCode;
        }

        public void setAlternateProcedureCode(String alternateProcedureCode) {
            this.alternateProcedureCode = alternateProcedureCode;
        }

        public Number getChargeAmount() {
            return chargeAmount;
        }

        public void setChargeAmount(Number chargeAmount) {
            this.chargeAmount = chargeAmount;
        }

        public Number getFeeScheduleAmount() {
            return feeScheduleAmount;
        }

        public void setFeeScheduleAmount(Number feeScheduleAmount) {
            this.feeScheduleAmount = feeScheduleAmount;
        }

        public Number getDefaultNonCoveredRate() {
            return defaultNonCoveredRate;
        }

        public void setDefaultNonCoveredRate(Number defaultNonCoveredRate) {
            this.defaultNonCoveredRate = defaultNonCoveredRate;
        }

        public Boolean getExclusion() {
            return exclusion;
        }

        public void setExclusion(Boolean exclusion) {
            this.exclusion = exclusion;
        }

        public String getExclusionReason() {
            return exclusionReason;
        }

        public void setExclusionReason(String exclusionReason) {
            this.exclusionReason = exclusionReason;
        }

        public Number getUnits() {
            return units;
        }

        public void setUnits(Number units) {
            this.units = units;
        }

        public Boolean getCapitation() {
            return isCapitation;
        }

        public void setCapitation(Boolean capitation) {
            isCapitation = capitation;
        }
    }
}
