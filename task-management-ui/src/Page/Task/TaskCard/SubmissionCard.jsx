import { Button, IconButton } from "@mui/material";
import OpenInNewIcon from '@mui/icons-material/OpenInNew';
import CheckIcon from '@mui/icons-material/Check';
import CloseIcon from '@mui/icons-material/Close';
import { useDispatch } from "react-redux";
import { acceptOrDeclineSubmission } from "../../../ReduxToolkit/Slices/SubmissionSlice";

export default function SubmissionCard({ item }) {

    const dispatch = useDispatch();

    const handleAcceptDecline = (status) => {
        dispatch(acceptOrDeclineSubmission({submissionId:item.id, status}));
        console.log(status);
    }

    return (
        <div className="rounded-md bg-black p-2 flex items-center justify-between">
            <div className="space-y-2">
                <div className="flex items-center gap-2">
                    <span>Repository</span>
                    <div className="flex items-center gap-2 text-[#c24dd0]">
                        <OpenInNewIcon />
                        <a href={item.repoLink} target="_blank" rel="noopener noreferrer">
                            Link
                        </a>
                    </div>
                </div>

                <div className="flex items-center gap-2 text-xs" >
                    <p>Submission Time : </p>
                    <p className="text-gray-400">{item.submissionTime}</p>
                </div>
            </div>
            <div>
                {
                    item.status === 'PENDING' ?
                        <div className="flex gap-5">
                            <div className="text-green-500">
                                <IconButton color="success" onClick={() => handleAcceptDecline(true)}>
                                    <CheckIcon />
                                </IconButton>
                            </div>
                            <div className="text-red-500">
                                <IconButton color="error" onClick={() => handleAcceptDecline(false)}>
                                    <CloseIcon />
                                </IconButton>
                            </div>
                        </div> 
                        :
                        <Button size="small" variant="outlined" color={item.status==='ACCEPTED' ? "success" : "error"}>{item.status}</Button>
                }
            </div>
        </div>
    );
}
