/**
 * Takapelastin serves as the back-end for my solution for Reaktor's
 * <a href="https://assignments.reaktor.com/birdnest/"><code>PROJECT BIRDNEST</code></a> assignment.
 * <p>
 * The project consists of two major parts:
 * <ol>
 *     <li>
 *         The {@link blue.lhf.takapelastin.checker.ViolationChecker}. This part is responsible for accepting data
 *         from endpoints provided by Reaktor, aggregating it, and providing a higher-level overview with additional
 *         information about violations of the no-drone zone. It contains most of the business logic for solving the
 *         problem statement, and it's a good place to start.
 *     </li>
 *     <li>
 *         The {@link blue.lhf.takapelastin.server.StatusServer}. This part is responsible for serving the data
 *         gathered and constructed by {@link blue.lhf.takapelastin.checker.ViolationChecker} via JSON over HTTP.
 *     </li>
 * </ol>
 *
 * These parts are configured and orchestrated in {@link blue.lhf.takapelastin.Application}, which does little more than
 * start both of the services.
 * */
package blue.lhf.takapelastin;