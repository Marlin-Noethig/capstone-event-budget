import * as React from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import Slide from '@mui/material/Slide';
import {TransitionProps} from '@mui/material/transitions';
import {Position} from "../model/Position";
import {useState} from "react";
import {EventData} from "../model/EventData";
import {SubCategory} from "../model/SubCategory";
import {useNavigate} from "react-router-dom";

type DeletePositionProps = {
    position?: Position
    event?: EventData
    subCategory?: SubCategory
    deleteFunction: (id: string, name: string) => void
}

const Transition = React.forwardRef(function Transition(
    props: TransitionProps & {
        children: React.ReactElement<any, any>;
    },
    ref: React.Ref<unknown>,
) {
    return <Slide direction="up" ref={ref} {...props} />;
});

export default function DeletionDialogue({position, event, subCategory, deleteFunction}:DeletePositionProps) {
    const [open, setOpen] = useState(false);
    const navigate = useNavigate();

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const onYes = () => {
        if(position){
            deleteFunction(position.id, position.name)
            handleClose()
        }
        if(event){
            deleteFunction(event.id, event.name)
            handleClose()
            navigate("/admin")
        }
        if(subCategory){
            deleteFunction(subCategory.id, subCategory.name)
            handleClose()
        }
    }

    const initiateDialogueTitle = () => {
      if (position){
          return `Delete Position ${position.name}?`
      }
      if (event){
          return `Delete event ${event.name}?`
      }
      if (subCategory){
          return `Delete sub category ${subCategory.name}?`
      }
    }

    const initiateDialogueText = () => {
        if (position) {
            return `Do you want to remove ${position.name}? This removal is irreversible.`
        }
        if (event) {
            return `Do you want to remove ${event.name}? All corresponding positions to this event will be removed, too. This removal is irreversible.`
        }
        if (subCategory) {
            return `Do you want to remove ${subCategory.name}?  All corresponding positions to this sub category will be removed, too. This removal is irreversible.`
        }
    }

    const dialogueTitle = initiateDialogueTitle()
    const dialogueText = initiateDialogueText()

    return (
        <div>
            <button onClick={handleClickOpen}>
                delete
            </button>
            <Dialog
                open={open}
                TransitionComponent={Transition}
                keepMounted
                onClose={handleClose}
                aria-describedby="alert-dialog-slide-description"
            >
                <DialogTitle>{dialogueTitle}</DialogTitle>
                <DialogContent>
                    <DialogContentText id="alert-dialog-slide-description">
                        {dialogueText}
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={onYes}>Yes</Button>
                    <Button onClick={handleClose}>No</Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}
