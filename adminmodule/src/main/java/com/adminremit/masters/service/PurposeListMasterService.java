package com.adminremit.masters.service;

import com.adminremit.auth.exception.RecordNotFoundException;
import com.adminremit.masters.dto.PurposeListDTO;
import com.adminremit.masters.models.PurposeListMaster;
import java.util.List;
import java.util.Optional;

public interface PurposeListMasterService {
    public PurposeListMaster save(PurposeListMaster purposeListMaster);
    public List<PurposeListMaster> listOfPurposeList();
    public PurposeListMaster getById(Long id) throws RecordNotFoundException;
    public void deletePurposeList(Long purposeId) throws RecordNotFoundException;

    Optional<PurposeListMaster> getPurposeById(Long purposeId);

    void approvePurpose(PurposeListMaster purpose);

    void rejectPurpose(PurposeListMaster purpose);
}
