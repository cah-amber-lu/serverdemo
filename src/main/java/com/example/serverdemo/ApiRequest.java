package com.example.serverdemo;


import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class ApiRequest {

        private String insuranceFilingType;         // Insurance filing type (MB, PPO, etc.).
        private String planName;                    // Customer plan name description.
        private String zipCode;                     // Service location zip code. Zip + 4 supported (no dash).
        private String state;                       // Service location state.
        private String taxId;                       // Tax ID for billing provider.
        private String patientBirthDate;            // Patient birth date.
        private String medicalGroup;                // Medicare group.
        private String thirdPartyAdministratorName; // Third party admin name.
        private String renderingProviderNpi;        // Rendering provider NPI.
        private List<ServiceLine> lines;            // Array of ServiceLine objects.

    public String getInsuranceFilingType() {
        return insuranceFilingType;
    }

    public void setInsuranceFilingType(String insuranceFilingType) {
        this.insuranceFilingType = insuranceFilingType;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getPatientBirthDate() {
        return patientBirthDate;
    }

    public void setPatientBirthDate(String patientBirthDate) {
        this.patientBirthDate = patientBirthDate;
    }

    public String getMedicalGroup() {
        return medicalGroup;
    }

    public void setMedicalGroup(String medicalGroup) {
        this.medicalGroup = medicalGroup;
    }

    public String getThirdPartyAdministratorName() {
        return thirdPartyAdministratorName;
    }

    public void setThirdPartyAdministratorName(String thirdPartyAdministratorName) {
        this.thirdPartyAdministratorName = thirdPartyAdministratorName;
    }

    public String getRenderingProviderNpi() {
        return renderingProviderNpi;
    }

    public void setRenderingProviderNpi(String renderingProviderNpi) {
        this.renderingProviderNpi = renderingProviderNpi;
    }

    public List<ServiceLine> getLines() {
        return lines;
    }

    public void setLines(List<ServiceLine> lines) {
        this.lines = lines;
    }

    /**
     * ServiceLine object class.
     */
    public static class ServiceLine {
        Number eligibilityPayerId;      // NHXS unique identifier (returned from Insurance Verification).
        String procedureCode;           // HCPC code.
        String vendorCode;              // Customer specific vendor code.
        String itemNumber;              // Customer specific item number.
        String modifierCode;            // Modifier code.
        String procedureGroup;          // Customer specific procedure group.
        Number retailAmount;            // Retail amount.
        Number cost;                    // Cost.
        Number units;                   // Units.
        Number extendedUnits;

        public ServiceLine(String procedureCode, String itemNumber) {
            this.eligibilityPayerId = 6;
            this.procedureCode = procedureCode;
            this.itemNumber = itemNumber;
            this.modifierCode = "";
            this.vendorCode = "TT";
            this.procedureGroup = "none";
            this.retailAmount = 20;
            this.cost = 1;
            this.units = 1;
            this.extendedUnits = 0;
        }

        public Number getEligibilityPayerId() {
            return eligibilityPayerId;
        }

        public void setEligibilityPayerId(Number eligibilityPayerId) {
            this.eligibilityPayerId = eligibilityPayerId;
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

        public Number getUnits() {
            return units;
        }

        public void setUnits(Number units) {
            this.units = units;
        }

        public Number getExtendedUnits() {
            return extendedUnits;
        }

        public void setExtendedUnits(Number extendedUnits) {
            this.extendedUnits = extendedUnits;
        }
    }

    List<ApiRequest> parse (File f) throws IOException {
        String request = new String(Files.readAllBytes(f.toPath()));
        Gson gson = new Gson();
        return Arrays.asList(gson.fromJson(request, ApiRequest[].class));
    }

}
