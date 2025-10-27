import React, { useState, useMemo } from 'react';

// --- ICONS ---
const TimeIcon: React.FC = () => <svg xmlns="http://www.w3.org/2000/svg" className="h-4 w-4 mr-1.5 text-rose-500" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" /></svg>;
const LocationIcon: React.FC = () => <svg xmlns="http://www.w3.org/2000/svg" className="h-4 w-4 mr-1.5 text-rose-500" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0zM15 11a3 3 0 11-6 0 3 3 0 016 0z" /></svg>;
const CloseIcon: React.FC = () => <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" /></svg>;
const OrganizerIcon: React.FC = () => <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5 mr-2 text-rose-400" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.653-.284-1.255-.758-1.685M12 12a3 3 0 100-6 3 3 0 000 6zM6 20v-2a3 3 0 015.356-1.857M6 20H2v-2a3 3 0 015-2.828" /></svg>;
const ChevronLeftIcon: React.FC = () => <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 19l-7-7 7-7" /></svg>;
const ChevronRightIcon: React.FC = () => <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 5l7 7-7 7" /></svg>;


// --- TYPES & DATA ---
interface Event {
    id: string;
    title: string;
    subtitle: string;
    time: string;
    date: string; // YYYY-MM-DD
    location: string;
    imageUrl: string;
    category: 'Tech' | 'Cultural' | 'Sports' | 'Workshop' | 'Talk';
    action: 'Join Event' | 'View Details' | 'Register';
    buttonColor: string;
    description: string;
    organizer: string;
    coords?: { lat: number; lon: number };
}

const events: Event[] = [
    { id: 'tech-1', title: 'AI & Machine Learning Conference', subtitle: 'A deep dive into the future of AI', time: '5:00 PM - 8:00 PM', date: '2024-11-10', location: 'TP Ganesan Auditorium', imageUrl: 'https://picsum.photos/seed/event1/500/300', category: 'Tech', action: 'Register', buttonColor: 'bg-rose-400 hover:bg-rose-500', description: 'Join industry experts and academic leaders as they discuss the future of Artificial Intelligence.', organizer: 'Dept. of Computer Science', coords: { lat: 12.8225, lon: 80.0441 } },
    { id: 'cultural-1', title: 'Milan - Inaugural Night', subtitle: 'Celebrity Music Performances', time: '6:00 PM onwards', date: '2024-11-15', location: 'Main Football Ground', imageUrl: 'https://picsum.photos/seed/event2/500/300', category: 'Cultural', action: 'View Details', buttonColor: 'bg-teal-300 hover:bg-teal-400', description: 'The grand opening of our annual cultural fest, featuring top artists from around the country.', organizer: 'Student Council', coords: { lat: 12.8260, lon: 80.0475 } },
    { id: 'sports-1', title: 'Inter-Department Cricket Finals', subtitle: 'CSE vs Mechanical', time: '2:00 PM - 5:00 PM', date: '2024-11-20', location: 'Main Sports Ground', imageUrl: 'https://picsum.photos/seed/event3/500/300', category: 'Sports', action: 'View Details', buttonColor: 'bg-orange-300 hover:bg-orange-400', description: 'The thrilling final match to decide this year\'s cricket champion.', organizer: 'Dept. of Sports', coords: { lat: 12.8260, lon: 80.0475 } },
    { id: 'workshop-1', title: 'React Hooks Workshop', subtitle: 'Beginner-friendly session', time: '10:00 AM - 1:00 PM', date: '2024-11-12', location: 'Tech Park, Lab 404', imageUrl: 'https://picsum.photos/seed/event4/500/300', category: 'Workshop', action: 'Register', buttonColor: 'bg-rose-400 hover:bg-rose-500', description: 'Learn the fundamentals of React Hooks in this hands-on workshop.', organizer: 'SRM Coding Club', coords: { lat: 12.8249, lon: 80.0468 } },
    { id: 'talk-1', title: 'Entrepreneurship in the Digital Age', subtitle: 'Guest lecture by CEO of Innovate Inc.', time: '2:00 PM - 3:30 PM', date: '2024-11-22', location: 'SJT Building, Room 405', imageUrl: 'https://picsum.photos/seed/event5/500/300', category: 'Talk', action: 'Register', buttonColor: 'bg-red-300 hover:bg-red-400', description: 'A special lecture by Ms. Aarti Sharma on her journey as an entrepreneur.', organizer: 'Career Development Center', coords: { lat: 12.8235, lon: 80.0453 } },
    { id: 'tech-2', title: 'HackSRM 6.0 Kick-off', subtitle: '24-hour hackathon', time: 'All Day', date: '2024-12-05', location: 'Tech Park', imageUrl: 'https://picsum.photos/seed/event6/500/300', category: 'Tech', action: 'Register', buttonColor: 'bg-rose-400 hover:bg-rose-500', description: 'The biggest coding challenge on campus is back with exciting new tracks.', organizer: 'SRM HackerEarth', coords: { lat: 12.8249, lon: 80.0468 } },
];
const categories = ['All', 'Tech', 'Cultural', 'Sports', 'Workshop', 'Talk'];
const dateFilters = ['All Dates', 'Today', 'This Week', 'This Month'];

// --- MODAL ---
const DetailsModal: React.FC<{ event: Event; onClose: () => void, onRegister: (id: string) => void, isRegistered: boolean }> = ({ event, onClose, onRegister, isRegistered }) => (
    <div className="fixed inset-0 bg-black/60 z-[100] flex items-center justify-center p-4" onClick={onClose}>
        <div className="bg-white rounded-2xl shadow-xl w-full max-w-lg p-8 relative" onClick={e => e.stopPropagation()}>
            <button onClick={onClose} className="absolute top-4 right-4 text-rose-400 hover:text-rose-600"><CloseIcon /></button>
            <h2 className="text-3xl font-bold text-rose-900">{event.title}</h2>
            <p className="text-rose-600 mt-1 text-lg">{event.subtitle}</p>
            <div className="mt-6 text-left space-y-4">
                <div className="flex items-center text-rose-800"><OrganizerIcon /> <strong>Organizer:</strong> &nbsp;{event.organizer}</div>
                <div className="flex items-center text-rose-800"><TimeIcon /> {new Date(event.date).toDateString()} @ {event.time}</div>
                <div className="flex items-center text-rose-800"><LocationIcon /> {event.location}</div>
                <div className="bg-lime-50 p-4 rounded-lg border border-lime-200"><p className="text-rose-700">{event.description}</p></div>
            </div>
            <button onClick={() => onRegister(event.id)} disabled={isRegistered} className={`mt-6 w-full py-3 text-white font-semibold rounded-lg shadow transition-colors ${isRegistered ? 'bg-teal-400' : event.buttonColor}`}>
                {isRegistered ? 'Registered' : event.action}
            </button>
        </div>
    </div>
);

// --- VIEWS ---
const EventCard: React.FC<{ event: Event; onAction: (event: Event) => void; isRegistered: boolean; }> = ({ event, onAction, isRegistered }) => {
    const categoryColors = {
        'Tech': 'bg-blue-100 text-blue-800', 'Cultural': 'bg-purple-100 text-purple-800',
        'Sports': 'bg-orange-100 text-orange-800', 'Workshop': 'bg-yellow-100 text-yellow-800',
        'Talk': 'bg-green-100 text-green-800'
    };
    return (
        <div className="bg-white rounded-lg shadow-md overflow-hidden transform hover:-translate-y-1 transition-transform duration-300 flex flex-col">
            <img src={event.imageUrl} alt={event.title} className="w-full h-40 object-cover" />
            <div className="p-4 flex flex-col flex-grow">
                <div>
                    <span className={`text-xs font-semibold px-2 py-1 rounded-full ${categoryColors[event.category]}`}>{event.category}</span>
                    <h3 className="font-bold text-xl text-rose-900 mt-2">{event.title}</h3>
                    <p className="text-sm text-rose-600 mb-3">{event.subtitle}</p>
                    <div className="text-sm text-rose-700 space-y-1">
                        <span className="flex items-center"><TimeIcon /> {new Date(event.date).toDateString()}</span>
                        <span className="flex items-center"><LocationIcon /> {event.location}</span>
                    </div>
                </div>
                <div className="mt-auto pt-4">
                    <button onClick={() => onAction(event)} disabled={isRegistered} className={`w-full py-2 rounded-lg text-white font-semibold transition-colors ${isRegistered ? 'bg-teal-400' : event.buttonColor}`}>
                        {isRegistered ? 'Registered' : 'RSVP / Details'}
                    </button>
                </div>
            </div>
        </div>
    );
};

const CalendarView: React.FC<{ events: Event[]; onEventClick: (event: Event) => void }> = ({ events, onEventClick }) => {
    const [currentDate, setCurrentDate] = useState(new Date());

    const calendarGrid = useMemo(() => {
        const year = currentDate.getFullYear();
        const month = currentDate.getMonth();
        const firstDayOfMonth = new Date(year, month, 1).getDay();
        const daysInMonth = new Date(year, month + 1, 0).getDate();
        const days = [];
        for (let i = 0; i < firstDayOfMonth; i++) days.push(null);
        for (let day = 1; day <= daysInMonth; day++) days.push(new Date(year, month, day));
        return days;
    }, [currentDate]);

    const eventsByDate = useMemo(() => {
        return events.reduce((acc, event) => {
            (acc[event.date] = acc[event.date] || []).push(event);
            return acc;
        }, {} as Record<string, Event[]>);
    }, [events]);

    const changeMonth = (offset: number) => {
        setCurrentDate(prev => {
            const newDate = new Date(prev);
            newDate.setMonth(newDate.getMonth() + offset);
            return newDate;
        });
    };

    return (
        <div className="bg-white rounded-2xl p-6 shadow-lg border">
            <div className="flex justify-between items-center mb-4">
                <button onClick={() => changeMonth(-1)} className="p-2 rounded-full hover:bg-lime-100"><ChevronLeftIcon /></button>
                <h3 className="text-xl font-bold text-rose-900">{currentDate.toLocaleString('default', { month: 'long', year: 'numeric' })}</h3>
                <button onClick={() => changeMonth(1)} className="p-2 rounded-full hover:bg-lime-100"><ChevronRightIcon /></button>
            </div>
            <div className="grid grid-cols-7 gap-1 text-center font-semibold text-rose-700 mb-2">
                {['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'].map(day => <div key={day} className="py-2">{day}</div>)}
            </div>
            <div className="grid grid-cols-7 gap-2">
                {calendarGrid.map((day, i) => (
                    <div key={i} className={`h-28 p-1 border rounded-lg overflow-hidden ${day ? 'bg-white' : 'bg-lime-100'} ${day && day.toDateString() === new Date().toDateString() ? 'border-2 border-rose-400' : 'border-lime-200'}`}>
                        {day && <span className="font-semibold text-rose-800 text-xs">{day.getDate()}</span>}
                        <div className="space-y-1 mt-1 overflow-y-auto max-h-20">
                            {(day && eventsByDate[day.toISOString().split('T')[0]] || []).map(event => (
                                <div key={event.id} onClick={() => onEventClick(event)} className="p-1 rounded text-xs bg-rose-100 text-rose-800 cursor-pointer hover:bg-rose-200">
                                    <p className="font-semibold truncate">{event.title}</p>
                                </div>
                            ))}
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};


const MapView: React.FC<{ events: Event[]; onEventClick: (event: Event) => void }> = ({ events, onEventClick }) => {
    const [selectedEventId, setSelectedEventId] = useState<string | null>(null);
    const [mapUrl, setMapUrl] = useState(`https://demo.f4map.com/#lat=12.8232&lon=80.0444&zoom=17&pitch=50`);

    const handleEventSelect = (event: Event) => {
        setSelectedEventId(event.id);
        if (event.coords) {
            setMapUrl(`https://demo.f4map.com/#lat=${event.coords.lat}&lon=${event.coords.lon}&zoom=18.5&pitch=60`);
        }
    };
    
    return (
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
            <div className="lg:col-span-1 h-[600px] bg-white rounded-2xl shadow-lg border p-4 flex flex-col">
                <h3 className="text-xl font-bold text-rose-900 mb-4 px-2">Events List</h3>
                <div className="flex-grow overflow-y-auto pr-2 space-y-3">
                    {events.map(event => (
                        <div key={event.id} onClick={() => handleEventSelect(event)} className={`p-3 rounded-lg cursor-pointer border-2 ${selectedEventId === event.id ? 'border-rose-400 bg-lime-50' : 'bg-white border-transparent hover:border-lime-300'}`}>
                            <p className="font-semibold text-rose-900">{event.title}</p>
                            <p className="text-sm text-rose-600">{event.location}</p>
                        </div>
                    ))}
                </div>
            </div>
            <div className="lg:col-span-2 h-[600px] rounded-2xl shadow-lg border overflow-hidden">
                 <iframe key={mapUrl} width="100%" height="100%" src={mapUrl} title="Event Map"></iframe>
            </div>
        </div>
    );
};


// --- MAIN COMPONENT ---
const UpcomingEvents: React.FC = () => {
    const [view, setView] = useState<'card' | 'calendar' | 'map'>('card');
    const [searchTerm, setSearchTerm] = useState('');
    const [activeCategory, setActiveCategory] = useState('All');
    const [dateFilter, setDateFilter] = useState('All Dates');
    const [registeredEvents, setRegisteredEvents] = useState<Set<string>>(new Set());
    const [modalEvent, setModalEvent] = useState<Event | null>(null);

    const filteredEvents = useMemo(() => {
        const now = new Date();
        const today = now.setHours(0, 0, 0, 0);
        const startOfWeek = new Date(today);
        startOfWeek.setDate(startOfWeek.getDate() - startOfWeek.getDay());
        const endOfWeek = new Date(startOfWeek);
        endOfWeek.setDate(endOfWeek.getDate() + 6);

        return events
            .filter(e => searchTerm === '' || e.title.toLowerCase().includes(searchTerm.toLowerCase()) || e.organizer.toLowerCase().includes(searchTerm.toLowerCase()))
            .filter(e => activeCategory === 'All' || e.category === activeCategory)
            .filter(e => {
                if (dateFilter === 'All Dates') return true;
                const eventDate = new Date(e.date).setHours(0,0,0,0);
                if (dateFilter === 'Today') return eventDate === today;
                if (dateFilter === 'This Week') return eventDate >= startOfWeek.getTime() && eventDate <= endOfWeek.getTime();
                if (dateFilter === 'This Month') return new Date(e.date).getMonth() === now.getMonth();
                return true;
            });
    }, [searchTerm, activeCategory, dateFilter]);
    
    const handleRegister = (eventId: string) => {
        setRegisteredEvents(prev => new Set(prev).add(eventId));
    };

    return (
        <section className="py-20 bg-white">
            <div className="container mx-auto px-4">
                <div className="text-center mb-12">
                     <h2 className="text-3xl md:text-4xl font-extrabold text-rose-900">Upcoming Campus Events</h2>
                     <p className="text-lg text-rose-600 max-w-2xl mx-auto">Discover, filter, and plan your schedule with our interactive event board.</p>
                </div>

                <div className="bg-lime-50 rounded-2xl p-4 mb-8 space-y-4 border">
                    <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
                        <input type="text" placeholder="Search events..." value={searchTerm} onChange={e => setSearchTerm(e.target.value)} className="w-full p-3 rounded-lg border-lime-200 focus:ring-rose-400 focus:border-rose-400"/>
                        <select value={activeCategory} onChange={e => setActiveCategory(e.target.value)} className="w-full p-3 rounded-lg border-lime-200 focus:ring-rose-400 focus:border-rose-400"><option disabled>Category</option>{categories.map(c => <option key={c}>{c}</option>)}</select>
                        <select value={dateFilter} onChange={e => setDateFilter(e.target.value)} className="w-full p-3 rounded-lg border-lime-200 focus:ring-rose-400 focus:border-rose-400"><option disabled>Date</option>{dateFilters.map(d => <option key={d}>{d}</option>)}</select>
                    </div>
                     <div className="flex justify-center items-center bg-lime-100 p-1 rounded-lg">
                        <button onClick={() => setView('card')} className={`flex-1 py-2 rounded-md font-semibold ${view==='card' ? 'bg-white shadow text-rose-600' : 'text-rose-500'}`}>Card View</button>
                        <button onClick={() => setView('calendar')} className={`flex-1 py-2 rounded-md font-semibold ${view==='calendar' ? 'bg-white shadow text-rose-600' : 'text-rose-500'}`}>Calendar View</button>
                        <button onClick={() => setView('map')} className={`flex-1 py-2 rounded-md font-semibold ${view==='map' ? 'bg-white shadow text-rose-600' : 'text-rose-500'}`}>Map View</button>
                    </div>
                </div>
                
                {view === 'card' && (
                    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
                        {filteredEvents.map(event => <EventCard key={event.id} event={event} onAction={setModalEvent} isRegistered={registeredEvents.has(event.id)} />)}
                    </div>
                )}
                {view === 'calendar' && <CalendarView events={filteredEvents} onEventClick={setModalEvent} />}
                {view === 'map' && <MapView events={filteredEvents} onEventClick={setModalEvent} />}

                {filteredEvents.length === 0 && (
                    <div className="text-center py-12 bg-white rounded-lg shadow-sm">
                        <p className="text-xl text-rose-800 font-semibold">No events match your criteria.</p>
                        <p className="text-rose-500">Try adjusting your filters.</p>
                    </div>
                )}
            </div>
            {modalEvent && <DetailsModal event={modalEvent} onClose={() => setModalEvent(null)} onRegister={handleRegister} isRegistered={registeredEvents.has(modalEvent.id)} />}
        </section>
    );
};

export default UpcomingEvents;
