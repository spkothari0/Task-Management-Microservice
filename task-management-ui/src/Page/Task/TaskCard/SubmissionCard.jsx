import { Avatar, Button, IconButton, ListItem, ListItemAvatar, ListItemText } from "@mui/material";
import OpenInNewIcon from '@mui/icons-material/OpenInNew';
import CheckIcon from '@mui/icons-material/Check';
import CloseIcon from '@mui/icons-material/Close';

export default function SubmissionCard() {

    const handleAcceptDecline = (status) => {
        console.log(status);
    }

    return (
        <div className="rounded-md bg-black p-2 flex items-center justify-between">
            <div className="space-y-2">
                <div className="flex items-center gap-2">
                    <span>Repository</span>
                    <div className="flex items-center gap-2 text-[#c24dd0]">
                        <OpenInNewIcon />
                        <a href="https://github.com/spkothari0" target="_blank" rel="noopener noreferrer">
                            Link
                        </a>
                    </div>
                </div>

                <div className="flex items-center gap-2 text-xs" >
                    <p>Submission Time : </p>
                    <p className="text-gray-400">2024-01-18T74:24:54</p>
                </div>
            </div>
            <div>
                {
                    true ?
                        <div className="flex gap-5">
                            <div className="text-green-500">
                                <IconButton color="success" onClick={()=>handleAcceptDecline("ACCEPT")}>
                                    <CheckIcon />
                                </IconButton>
                            </div>
                            <div className="text-red-500">
                                <IconButton color="error" onClick={()=>handleAcceptDecline("REJECT")}>
                                    <CloseIcon />
                                </IconButton>
                            </div>
                        </div> :
                        <Button size="small" variant="outlined" color={true ? "success" : "error"}>Accepted</Button>
                }
            </div>
        </div>
    );
}
