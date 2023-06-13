package com.example.demo;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class CRUDService {

    public String createCRUD(CRUD crud) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApiFuture = dbFirestore
                .collection("crud_user")
                .document(crud.getDocumentId())
                .set(crud, SetOptions.merge());
        return collectionApiFuture.get().getUpdateTime().toString();
    }

    public CRUD getCRUD(String documentId) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("crud_user").document(documentId);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        CRUD crud;
        if(document.exists()){
            crud = document.toObject(CRUD.class);
            return crud;
        }
        return null;
    }

    public String updateCRUD(CRUD crud) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection("crud_user").document(crud.getDocumentId()).set(crud);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public String addRecordId(String documentId, int newRecordId) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("crud_user").document(documentId);

        // Обновить поле recordId, добавив новый элемент
        ApiFuture<WriteResult> updateFuture = documentReference.update("recordId", FieldValue.arrayUnion(newRecordId));

        return "Successfully added recordId to " + documentId;
    }


    public String deleteCRUD(String documentId){
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = dbFirestore.collection("crud_user").document(documentId).delete();
        return "Successfully deleted " + documentId;
    }

    public List<CRUD> getAllCRUD() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference collectionReference = dbFirestore.collection("crud_user");
        ApiFuture<QuerySnapshot> future = collectionReference.get();
        QuerySnapshot querySnapshot = future.get();

        List<CRUD> crudList = new ArrayList<>();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            CRUD crud = document.toObject(CRUD.class);
            crudList.add(crud);
        }

        return crudList;
    }
}
