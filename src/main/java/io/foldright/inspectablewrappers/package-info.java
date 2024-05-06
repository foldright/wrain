/**
 * 🪐 Inspectable Wrappers Specification provides a standard for wrapper chain with the inspection ability.
 *
 * <ul>
 * <li>The specification interfaces:
 *   <ul>
 *   <li>{@link io.foldright.inspectablewrappers.Wrapper} interface is used to be implemented
 *       by wrapper classes, make an <strong>inspectable wrapper chain</strong>(linked list).
 *   <li>{@link io.foldright.inspectablewrappers.Attachable} interface is used to
 *       enhance the wrapper instances with the attachment storage ability
 *   <li>{@link io.foldright.inspectablewrappers.WrapperAdapter} interface is used to adapt
 *       an existed wrapper instance to type {@link io.foldright.inspectablewrappers.Wrapper} without modifying it.
 *   </ul>
 * <li>The {@link io.foldright.inspectablewrappers.Inspector} class is used to inspect the wrapper chain.
 * </ul>
 *
 * <h2>About wrapper pattern</h2>
 *
 * <a href="https://refactoring.guru/design-patterns/decorator">Wrapper pattern(aka. Decorator pattern)</a> is
 * well-known and widely-used, is used to attach new behaviors to objects
 * (the <strong>wrappee</strong>/<strong>underlying instances</strong>/<strong>wrapped instances</strong>)
 * by placing these objects inside the <strong>wrapper</strong> objects that contain the behaviors.
 * <p>
 * Well-known examples of wrapper pattern in java std lib:
 * <ul>
 * <li>Collection Wrappers:<sup><a href="https://docs.oracle.com/javase/tutorial/collections/implementations/wrapper.html">
 *     (Wrapper Implementations - The Java™ Tutorials)</a></sup>
 *   <ul>
 *   <li>Unmodifiable Wrappers, e.g.
 *     {@link java.util.Collections#unmodifiableList(java.util.List) Collections#unmodifiableList(List)}
 *   <li>Synchronization Wrappers, e.g.
 *     {@link java.util.Collections#synchronizedCollection(java.util.Collection) Collections#synchronizedCollection(Collection)}
 *   </ul>
 * <li>{@link java.io.BufferedReader#BufferedReader(java.io.Reader) BufferedReader#BufferedReader(Reader)}
 * </ul>
 * <p>
 * The related design patterns are <a href="https://refactoring.guru/design-patterns/adapter">Adapter </a>
 * the wrapper instances and wra
 *
 * @author Jerry Lee (oldratlee at gmail dot com)
 * @author Zava Xu (zava dot kid at gmail dot com)
 * @author Yang Fang (snoop dot fy at gmail dot com)
 * @see io.foldright.inspectablewrappers.Wrapper
 * @see io.foldright.inspectablewrappers.Attachable
 * @see io.foldright.inspectablewrappers.WrapperAdapter
 * @see io.foldright.inspectablewrappers.Inspector
 */
package io.foldright.inspectablewrappers;
