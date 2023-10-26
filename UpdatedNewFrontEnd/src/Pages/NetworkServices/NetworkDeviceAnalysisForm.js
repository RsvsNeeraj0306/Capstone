import React, { useEffect, useState } from 'react';

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
                },
                body: JSON.stringify(analysisData),
            });

            if (response.ok) {
                const data = await response.json();
                alert(data.responseBody);
                onSetNetworkDeviceAnalysis(); // Notify the parent component about the successful analysis
            }
            else {
                alert('Failed to set network device analysis. Please check the network device ID.');
            }
        }
        catch (error) {
            console.error('Error:', error);
        }
        finally {
            resetForm();
            console.log(analysisData);
        }
    };

    useEffect(() => {
        const fetchNetworkDevices = async () => {
            try {
                const response = await fetch('http://localhost:8080/api/allHardware');
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
        <option value={networkDevice.id}>{networkDevice.id}</option>
    ));

    return (
        <div className="container">
            <h3> Network Device Analysis</h3>
            <form onSubmit={handleSetAnalysis}>
                <div className="form-group">
                    <label htmlFor="networkDeviceId">Network Device ID</label>
                    <select
                        className="form-control"
                        id="networkDeviceId"
                        value={networkDeviceId}
                        onChange={(e) => setNetworkDeviceId(e.target.value)}
                    >
                        <option value="">Select a network device ID</option>
                        {networkDeviceOptions}
                    </select>
                </div>
                <div className="form-group">
                    <label htmlFor="activeDevice">Active Device</label>
                    <input
                        type="text"
                        className="form-control"
                        id="activeDevice"
                        value={activeDevice}
                        onChange={(e) => setActiveDevice(e.target.value)}
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="averageTimeUsage">Average Time Usage</label>
                    <input
                        type="text"
                        className="form-control"
                        id="averageTimeUsage"
                        value={averageTimeUsage}
                        onChange={(e) => setAverageTimeUsage(e.target.value)}
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="companyRating">Company Rating</label>
                    <input
                        type="text"
                        className="form-control"
                        id="companyRating"
                        value={companyRating}
                        onChange={(e) => setCompanyRating(e.target.value)}
                    />
                </div>
                <button type="submit" className="btn btn-primary">Set Analysis</button>
            </form>
        </div>
    );
}


export default NetworkDeviceAnalysisForm;
