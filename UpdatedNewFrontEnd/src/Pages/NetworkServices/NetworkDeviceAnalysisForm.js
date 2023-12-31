import React, { useEffect, useState } from 'react';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';


function NetworkDeviceAnalysisForm({ onSetNetworkDeviceAnalysis }) {
    const [networkDeviceId, setNetworkDeviceId] = useState('');
    const [activeDevice, setActiveDevice] = useState('');
    const [averageTimeUsage, setAverageTimeUsage] = useState('');
    const [companyRating, setCompanyRating] = useState('');
    const [networkDevices, setNetworkDevices] = useState([]);

    const resetForm = () => {
        setNetworkDeviceId('');
        setActiveDevice('');
        setAverageTimeUsage('');
        setCompanyRating('');
    };

    const handleSetAnalysis = async (e) => {
        e.preventDefault();
    
        // Regular expression to check if the company rating is between 1 and 5.
        const ratingPattern = /^[1-5]$/;
    
        // Regular expression to check if the active time is in hours (e.g., 1.5, 2, 3.5).
        const activeTimePattern = /^\d+(\.\d+)?$/;
    
        if (!ratingPattern.test(companyRating)) {
            toast.error('Company Rating must be a number between 1 and 5.');
            return;
        }
    
        if (!activeTimePattern.test(averageTimeUsage)) {
            toast.error('Active Time must be a valid number in hours (e.g., 1.5, 2, 3.5).');
            return;
        }
    
        const analysisData = {
            networkDevice: {
                id: networkDeviceId,
            },
            networkDeviceAnalysis: {
                activeDevice,
                averageTimeUsage,
                companyRating,
            },
        };
    
        try {
            const response = await fetch('http://localhost:8080/api/setAnalysis', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + localStorage.getItem('token')
                },
                body: JSON.stringify(analysisData),
            });
    
            if (response.ok) {
                const data = await response.json();
                console.log(data);
                toast.success('Analysis done successfully!');
            } else {
                toast.error('Failed to set analysis. Please check the network device ID.');
            }
        } catch (error) {
            console.error('Error:', error);
        }
    
        resetForm();
    };    

    useEffect(() => {
        const fetchNetworkDevices = async () => {
            try {
                const response = await fetch('http://localhost:8080/api/allHardware', {
                    headers: {
                        'Authorization': 'Bearer ' + localStorage.getItem('token')
                    }
                });
                const data = await response.json();
                setNetworkDevices(data);
            }
            catch (error) {
                console.error('Error:', error);
            }
        };

        fetchNetworkDevices();
    }
        , []);

    const networkDeviceOptions = networkDevices.map((networkDevice) => (
        <option key={networkDevice.id} value={networkDevice.id}>
            {networkDevice.id}
        </option>
    ));

    return (
        <div className="form-container" style={{'top': '70%'}}>
            <h3> Network Device Analysis</h3>
            <form onSubmit={handleSetAnalysis}>
                <div>
                    <label >Network Device: </label>
                    <select
                        className="form-control"
                        id="networkDeviceId"
                        value={networkDeviceId}
                        onChange={(e) => setNetworkDeviceId(e.target.value)}
                    >
                        <option value="">Select ID</option>
                        {networkDeviceOptions}
                    </select>
                    <br />
                    <br />
                </div>
                <div>
                    <label>Active Device</label>
                    <input
                        type="text"
                        className="form-control"
                        id="activeDevice"
                        value={activeDevice}
                        onChange={(e) => setActiveDevice(e.target.value)}
                    />
                </div>
                <div >
                    <label>Average Time Usage(Hours)</label>
                    <input
                        type="text"
                        className="form-control"
                        id="averageTimeUsage"
                        value={averageTimeUsage}
                        onChange={(e) => setAverageTimeUsage(e.target.value)}
                    />
                </div>
                <br />
                <div>
    <label>Company Rating</label>
    <select
        className="form-control"
        id="companyRating"
        value={companyRating}
        onChange={(e) => setCompanyRating(e.target.value)}
    >
        <option value="">Select Rating</option>
        <option value="1">1</option>
        <option value="2">2</option>
        <option value="3">3</option>
        <option value="4">4</option>
        <option value="5">5</option>
    </select>
    <br />
    <br />
</div>
                <button type="submit"   className="form-button">Set Analysis</button>
            </form>

        </div>
    );
}



export default NetworkDeviceAnalysisForm;
