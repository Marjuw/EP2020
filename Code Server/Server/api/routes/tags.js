const express = require('express');
const router = express.Router();
const mongoose = require("mongoose");
const request = require("request");

const { v4: uuidv4 } = require('uuid');




const Tags = require("../models/tags");
const { ResourceNotFoundError, InternalError } = require('./errors.js');

//Erhalte alle Tags      //Brauchen wir  zum überprüfen (als programmierer) => Noch in Rest hinzufügen
router.get("/", (req, res, next) => {

    const query = req.query; // Falls Qury parameter angegeben werden wird gefiltert

    User.find(query)  // Direkt mit Query da wenn keine Query parameter eingegeben ist der "Filter auf null"
        .exec()
        .then(docs => {
            console.log(docs);
            res.status(200).json(docs);
        })
        .catch(err => {
            console.log(err);
            res.status(500).json({
                error: err
            });
        });
});

//erstelle einen Tag
router.post("/", (req, res, next) => {

    const tag = new Tag({

       // _id: uuidv4(),
        bezeichnung: req.body.bezeichnung,
        typ: req.body.typ
        
    });

    tag.save()
        .then(result => {
            console.log(result);
            res.status(201).json({
                message: "Handling POST requests to /tags",
                createdTag: result
            });
        })
        .catch(err => {
            console.log(err);
            res.status(500).json({
                error: err
            });
        });
});

//Erhalte bestimmten User
router.get("/:tagId", (req, res, next) => {

    const id = req.params.tagId;
    User.findById(id)
        .exec()
        .then(doc => {
            console.log("From database", doc);
            if (doc) {
                res.status(200).json(doc);
            } else {
                res
                    .status(404)
                    .json({ message: "No valid entry found for provided ID" });
            }
        })
        .catch(err => {
            console.log(err);
            res.status(500).json({ error: err });
        });

});

//Lösche bestimmten Tag
router.delete("/:tagId", (req, res, next) => {

    const id = req.params.tagId;
    User.remove({_id: id})
    //Hier muss noch hin was mit dem Projekt passiert, wenn dieser Tag eins erstellt hat
        .exec()
        .then(result => {
            res.status(200).json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(500).json({
                error: err
            });

        });
});

//Bearbeite/aktualisiere bestimmten Tag
//Zum Testen: [{"propName": <Attributname>, "value": <neuer Attributwert>}]
// ZB:  [{"propName": "nickname", "value": "NachUpdate"}]
/*router.put("/:userId", (req, res, next) => {

    const id = req.params.userId;
    const updateOps = {};
    for (const ops of req.body) {
        updateOps[ops.propName] = ops.value;
    }
    User.update({ _id: id }, { $set: updateOps })
        .exec()
        .then(result => {
            console.log(result);
            res.status(200).json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(500).json({
                error: err
            });
        });
});*/

// ZB: {"nickname": "NachUpdate"}
router.put("/:tagId", (req, res, next) => {
    const id = req.params.tagId;
   // const updateOps = {};
   // for (const ops of req.body) {
   //     updateOps[ops.propName] = ops.value;
   // }
    User.update({ _id: id }, { $set: req.body })
        .exec()
        .then(result => {
            console.log(result);
            res.status(200).json(result);
        })
        .catch(err => {
            console.log(err);
            res.status(500).json({
                error: err
            });
        });
});


module.exports = router;
