package com.baidubupt.coupletserver.service;

import com.baidubupt.coupletserver.entity.SecondCouplet;
import com.baidubupt.coupletserver.entity.SecondCoupletListEntity;
import com.baidubupt.coupletserver.server.ServerConfig;
import com.baidubupt.coupletserver.util.ProcessUtils;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.Callable;

@Service
public class CoupletServiceImpl implements CoupletService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoupletServiceImpl.class);

    /** Cache **/
    private static final Cache<String, SecondCoupletListEntity> stringSecondCoupletListEntityLoadingCache =
            CacheBuilder.newBuilder()
                    .maximumSize(1000).build();

    @Autowired
    ServerConfig serverConfig;

    @Override
    public SecondCoupletListEntity generate(String firstCouplet) throws Exception {
        return stringSecondCoupletListEntityLoadingCache.get(firstCouplet, new Callable<SecondCoupletListEntity>() {
            @Override
            public SecondCoupletListEntity call() throws Exception {
                makeSureDirectoryExist(serverConfig.getTmpFileDirectory());

                final String tmpFileDirectory = serverConfig.getTmpFileDirectory();
                final String inputFilePath = createInputFile(tmpFileDirectory, firstCouplet).getAbsolutePath();
                final String outputFilePath = createOutputFilePath(tmpFileDirectory);

                String command = buildCommand(inputFilePath, outputFilePath);

                LOGGER.info("invoke command {}.", command);
                long BEGIN = System.currentTimeMillis();
                Process process = ProcessUtils.execute(command);
                int existValue = process.waitFor();
                LOGGER.info("finish invoke command {}, exist value {}, using {} ms.", command, existValue, (System.currentTimeMillis() - BEGIN));

                return toSecondCoupletList(outputFilePath);
            }
        });
    }

    private SecondCoupletListEntity toSecondCoupletList(String outputFilePath) throws IOException {
        File outputFile = new File(outputFilePath);
        if (!outputFile.exists() || outputFile.length() == 0) {
            throw new RuntimeException("Output file not exist");
        }

        SecondCoupletListEntity secondCoupletListEntity = new SecondCoupletListEntity();
        try (Scanner scanner = new Scanner(new FileInputStream(outputFile))) {
            String firstCouplet = removeAllBlankSpace(scanner.nextLine());

            List<SecondCouplet> secondCoupletList = new ArrayList<SecondCouplet>(8);
            secondCoupletListEntity.setFirstCouplet(firstCouplet);
            while (scanner.hasNextLine()) {
                String singleLine = scanner.nextLine();
                int sepIndex = singleLine.indexOf('\t');

                if (sepIndex == -1) {
                    continue;
                }

                double score = 100.0D + Double.parseDouble(singleLine.substring(0, sepIndex));
                String secondCoupletStr = removeAllBlankSpace(singleLine.substring(sepIndex + 1));

                SecondCouplet secondCouplet = new SecondCouplet();
                secondCouplet.setScore(score);
                secondCouplet.setCouplet(secondCoupletStr);

                secondCoupletList.add(secondCouplet);
            }

            secondCoupletListEntity.setSecondCoupletList(secondCoupletList);
        }

        return secondCoupletListEntity;
    }

    private void makeSureDirectoryExist(String tmpFileDirectory) {
        File file = new File(tmpFileDirectory);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    private String buildCommand(String inputFilePath, String outputFilePath) {
        final StringBuilder commandStrBuilder = new StringBuilder();
        commandStrBuilder
                .append(serverConfig.getPythonPath())
                .append(' ')

                .append(serverConfig.getCoupletGeneratePythonFilePath())
                .append(' ')

                .append("--vocabs_path")
                .append(' ')
                .append(serverConfig.getVocabsPath())
                .append(' ')

                .append("--model_path")
                .append(' ')
                .append(serverConfig.getModelPath())
                .append(' ')

                .append("--test_data_path")
                .append(' ')
                .append(inputFilePath)
                .append(' ')

                .append("--save_file")
                .append(' ')
                .append(outputFilePath)
                .append(' ')

                .append("--beam_size")
                .append(' ')
                .append(5)
                .append(' ');
        return commandStrBuilder.toString();
    }

    private File createInputFile(String tmpFileDirectory, String firstCouplet) throws IOException {
        File inputFile = new File(tmpFileDirectory, UUID.randomUUID().toString());

        if (!inputFile.exists()) {
            inputFile.createNewFile();
        }

        try (FileWriter fileWriter = new FileWriter(inputFile)) {
            char[] charArray = firstCouplet.toCharArray();
            for (int i=0; i<charArray.length; i++) {
                fileWriter.write(charArray[i]);
                fileWriter.write(' ');
            }
        }

        return inputFile;
    }

    private String createOutputFilePath(String tmpFileDirectory) {
        return tmpFileDirectory + File.separator + UUID.randomUUID().toString();
    }

    static String removeAllBlankSpace(String str) {
        if (str == null) {
            return str;
        }

        return str.replaceAll(" ", "");
    }

}
