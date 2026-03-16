package com.dhatvibs.dashboard.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dhatvibs.dashboard.dto.DatasetRecordDTO;
import com.dhatvibs.dashboard.entity.DatasetRecord;
import com.dhatvibs.dashboard.repository.DatasetRecordRepository;
import com.dhatvibs.dashboard.service.DatasetRecordService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DatasetRecordServiceImpl implements DatasetRecordService {

    private final DatasetRecordRepository datasetRecordRepository;

    @Override
    public DatasetRecord saveRecord(DatasetRecordDTO dto) {

        DatasetRecord record = new DatasetRecord();

        record.setDatasetId(dto.getDatasetId());
        record.setCampaignId(dto.getCampaignId());
        record.setCampaignName(dto.getCampaignName());
        record.setPlatform(dto.getPlatform());
        record.setDate(dto.getDate());
        record.setImpressions(dto.getImpressions());
        record.setClicks(dto.getClicks());
        record.setLeads(dto.getLeads());
        record.setOrders(dto.getOrders());
        record.setRevenue(dto.getRevenue());
        record.setAdSpend(dto.getAdSpend());

        return datasetRecordRepository.save(record);
    }

    @Override
    public List<DatasetRecord> saveAll(List<DatasetRecordDTO> records) {

        List<DatasetRecord> entities =
                records.stream().map(dto -> {

                    DatasetRecord r = new DatasetRecord();

                    r.setDatasetId(dto.getDatasetId());
                    r.setCampaignId(dto.getCampaignId());
                    r.setCampaignName(dto.getCampaignName());
                    r.setPlatform(dto.getPlatform());
                    r.setDate(dto.getDate());
                    r.setImpressions(dto.getImpressions());
                    r.setClicks(dto.getClicks());
                    r.setLeads(dto.getLeads());
                    r.setOrders(dto.getOrders());
                    r.setRevenue(dto.getRevenue());
                    r.setAdSpend(dto.getAdSpend());

                    return r;

                }).collect(Collectors.toList());

        return datasetRecordRepository.saveAll(entities);
    }

    @Override
    public List<DatasetRecord> getByDatasetId(Long datasetId) {

        return datasetRecordRepository.findByDatasetId(datasetId);
    }
}