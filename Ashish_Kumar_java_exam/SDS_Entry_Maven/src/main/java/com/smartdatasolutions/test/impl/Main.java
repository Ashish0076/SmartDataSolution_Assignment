package com.smartdatasolutions.test.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.smartdatasolutions.test.Member;
import com.smartdatasolutions.test.MemberExporter;
import com.smartdatasolutions.test.MemberFileConverter;
import com.smartdatasolutions.test.MemberImporter;

public class Main extends MemberFileConverter {

	@Override
	protected MemberExporter getMemberExporter( ) {
		return new MemberExporterImpl();
	}

	@Override
	protected MemberImporter getMemberImporter( ) {
		 return new MemberImporterImpl();
	}

	@Override
	protected List< Member > getNonDuplicateMembers( List< Member > membersFromFile ) {
		Set<String> memberIds = new HashSet<>();
        List<Member> nonDuplicateMembers = new ArrayList<>();

        for (Member member : membersFromFile) {
            if (memberIds.add(member.getId())) {
                nonDuplicateMembers.add(member);
            }
        }

        return nonDuplicateMembers;
	}

	@Override
	protected Map< String, List< Member >> splitMembersByState( List< Member > validMembers ) {
		Map<String, List<Member>> membersByState = new HashMap<>();

        for (Member member : validMembers) {
            String state = member.getState();
            membersByState.computeIfAbsent(state, k -> new ArrayList<>()).add(member);
        }

        return membersByState;
	}

	public static void main( String[] args ) {
		Main main = new Main();
        File inputFile = new File("Members.txt");
        String outputDirectory = "C:\\Users\\ashis\\Desktop\\Ashish_Kumar_java_exam\\SDS_Entry_Maven";
        String outputFileName = "outputFile.csv";

        try {
            main.convert(inputFile, outputDirectory, outputFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
