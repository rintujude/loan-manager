package com.ldms.loanManager.LoanManagement;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ldms.loanManager.loanManagement.service.LoanService;
import com.ldms.loanManager.utils.CommonUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EnquiryTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LoanService loanService;

    @Test
    public void contextLoads() {
        Assertions.assertThat(loanService).isNotNull();
    }

    @Test
    public void calculateMonthlyRepaymentTestWithoutBalloonPayment() {
        double principalAmount = 20000;
        double monthlyInterestRate = 0.00625;
        int numberOfPayments = 12;

        double monthlyRepayment = loanService.calculateMonthlyRepaymentWithoutBalloon(principalAmount, monthlyInterestRate, numberOfPayments);
        Assertions.assertThat(monthlyRepayment).isEqualTo(1735.1483377081222);
    }

    @Test
    public void calculateMonthlyRepaymentTestWithBalloonPayment() {
        double principalAmount = 20000;
        double balloonPayment = 10000;
        double monthlyInterestRate = 0.075;
        int numberOfPayments = 12;

        double monthlyRepayment = loanService.calculateMonthlyRepaymentWithBalloon(principalAmount, balloonPayment, CommonUtils.round(monthlyInterestRate / 12.0,5), numberOfPayments);
        Assertions.assertThat(monthlyRepayment).isEqualTo(930.0741688540605);
    }

    @Test
    public void testAmortisationEndpointWithBalloonPayment() throws Exception {
        String requestBody = "{\"assetCost\":\"20000\", \"deposit\":\"0\"" +
                ", \"balloonPayment\":\"10000.0\", \"interestRate\":\"7.5\", \"numberOfPayments\":\"12\"}";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/loan/generate-schedule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.amortisation.schedule").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.amortisation.schedule[0].paymentAmount").value(930.07))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amortisation.schedule[0].principal").value(805.07))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amortisation.schedule[0].period").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amortisation.schedule[0].interest").value(125.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amortisation.schedule[0].balance").value(19194.93))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amortisation.schedule[11].paymentAmount").value(930.07))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amortisation.schedule[11].principal").value(862.19))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amortisation.schedule[11].period").value(12))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amortisation.schedule[11].interest").value(67.89))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amortisation.schedule[11].balance").value(10000.0))
                .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        mockMvc.perform(MockMvcRequestBuilders.delete("/loan/enquiry/{id}", jsonNode.get("id"))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testAmortisationEndpointWithoutBalloonPayment() throws Exception {
        String requestBody = "{\"assetCost\":\"20000\", \"deposit\":\"0\"" +
                ", \"balloonPayment\":\"0.0\", \"interestRate\":\"7.5\", \"numberOfPayments\":\"12\"}";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/loan/generate-schedule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.amortisation.schedule").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.amortisation.schedule[0].paymentAmount").value(1735.15))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amortisation.schedule[0].principal").value(1610.15))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amortisation.schedule[0].period").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amortisation.schedule[0].interest").value(125.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amortisation.schedule[0].balance").value(18389.85))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amortisation.schedule[11].paymentAmount").value(1735.15))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amortisation.schedule[11].principal").value(1724.37))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amortisation.schedule[11].period").value(12))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amortisation.schedule[11].interest").value(10.78))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amortisation.schedule[11].balance").value(0.0))
                .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        mockMvc.perform(MockMvcRequestBuilders.delete("/loan/enquiry/{id}", jsonNode.get("id"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
