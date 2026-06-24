package com.bajaj.bfhl.service.impl;

import com.bajaj.bfhl.dto.BfhlRequest;
import com.bajaj.bfhl.dto.BfhlResponse;
import com.bajaj.bfhl.service.BfhlService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class BfhlServiceImpl implements BfhlService {

    @Value("${bfhl.user.id}")
    private String userId;

    @Value("${bfhl.user.email}")
    private String email;

    @Value("${bfhl.user.roll-number}")
    private String rollNumber;

    @Override
    public BfhlResponse processData(BfhlRequest request) {
        List<String> oddNumbers = new ArrayList<>();
        List<String> evenNumbers = new ArrayList<>();
        List<String> alphabets = new ArrayList<>();
        List<String> specialCharacters = new ArrayList<>();
        BigInteger totalSum = BigInteger.ZERO;
        
        List<String> dataList = request.data();
        if (dataList != null) {
            for (String item : dataList) {
                if (item == null) {
                    continue;
                }
                
                String trimmed = item.trim();
                if (trimmed.isEmpty()) {
                    specialCharacters.add(item);
                    continue;
                }

                if (isNumeric(trimmed)) {
                    BigInteger val = new BigInteger(trimmed);
                    if (val.testBit(0)) {
                        oddNumbers.add(trimmed);
                    } else {
                        evenNumbers.add(trimmed);
                    }
                    totalSum = totalSum.add(val);
                } else if (isAlphabetic(trimmed)) {
                    alphabets.add(trimmed.toUpperCase());
                } else {
                    specialCharacters.add(item);
                }
            }
        }

        String concatString = generateConcatString(dataList);

        return new BfhlResponse(
            true,
            userId,
            email,
            rollNumber,
            oddNumbers,
            evenNumbers,
            alphabets,
            specialCharacters,
            totalSum.toString(),
            concatString
        );
    }

    private boolean isNumeric(String str) {
        return str.matches("^-?\\d+$");
    }

    private boolean isAlphabetic(String str) {
        return str.matches("^[a-zA-Z]+$");
    }

    private String generateConcatString(List<String> dataList) {
        if (dataList == null) {
            return "";
        }
        
        StringBuilder alphabeticOnly = new StringBuilder();
        for (String s : dataList) {
            if (s != null) {
                for (char c : s.toCharArray()) {
                    if (Character.isLetter(c)) {
                        alphabeticOnly.append(c);
                    }
                }
            }
        }
        
        String reversed = alphabeticOnly.reverse().toString();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < reversed.length(); i++) {
            char c = reversed.charAt(i);
            if (i % 2 == 0) {
                result.append(Character.toUpperCase(c));
            } else {
                result.append(Character.toLowerCase(c));
            }
        }
        return result.toString();
    }
}
